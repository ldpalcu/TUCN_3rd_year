----------------------------------------------------------------------------------
-- Company: 
-- Engineer: 
-- 
-- Create Date: 05/02/2018 10:27:40 AM
-- Design Name: 
-- Module Name: main_module - Behavioral
-- Project Name: 
-- Target Devices: 
-- Tool Versions: 
-- Description: 
-- 
-- Dependencies: 
-- 
-- Revision:
-- Revision 0.01 - File Created
-- Additional Comments:
-- 
----------------------------------------------------------------------------------


library IEEE;
use IEEE.STD_LOGIC_1164.ALL;

-- Uncomment the following library declaration if using
-- arithmetic functions with Signed or Unsigned values
--use IEEE.NUMERIC_STD.ALL;

-- Uncomment the following library declaration if instantiating
-- any Xilinx leaf cells in this code.
--library UNISIM;
--use UNISIM.VComponents.all;

entity main_module is
    Generic (Dim_Cuv   : INTEGER := 8);
    Port ( Clk : in STD_LOGIC;
           Rst : in STD_LOGIC;
           Read : in STD_LOGIC;
           MISO : in STD_LOGIC;
           MOSI : out STD_LOGIC;
           SCLK : out STD_LOGIC;
           SS : out STD_LOGIC;
           Data1 : out STD_LOGIC_VECTOR (31 downto 0);
           Data2 : out STD_LOGIC_VECTOR (31 downto 0));
end main_module;

architecture Behavioral of main_module is
    signal Start, TxRdy, RxRdy : STD_LOGIC;
    signal TxData, RxData : STD_LOGIC_VECTOR(Dim_Cuv - 1 downto 0);
    signal Contor : INTEGER := 0;
    signal Comanda : STD_LOGIC := '0';
    
    type TIP_STARE is (idle,s1, s2, s3, s4, s5, s6, s7, s8);
    signal Stare : TIP_STARE;
    
    type DATA is array(0 to 7) of STD_LOGIC_VECTOR(7 downto 0);
    signal DataInt : DATA;

begin

    SPI : entity WORK.controler_spi 
                generic map (Dim_Cuv => Dim_Cuv)
                port map (Clk => Clk,
                         Rst => Rst,
                         Start => Start,
                         TxData => TxData,
                         MISO => MISO,
                         MOSI => MOSI,
                         SCLK => SCLK,
                         SS => SS,
                         TxRdy => TxRdy,
                         RxRdy => RxRdy,
                         RxData => RxData);
                         
    UC : process(Clk)
    begin
        if rising_edge(Clk) then
            if Rst = '1' then
                Stare <= idle;
            else
                case Stare is
                    when idle => 
                        Contor <= 0;
                        if Read = '1' then
                            Stare <= s1;
                        end if;
                    when s1 =>
                        Stare <= s2;
                    when s2 =>
                        Stare <= s3;
                    when s3 =>
                        if TxRdy = '1' then
                            Stare <= s4;
                        end if;
                    when s4 =>
                        Stare <= s5;
                    when s5 =>
                        Stare <= s6;
                    when s6 =>
                        if RxRdy = '1' then
                            Stare <= s7;
                        end if;
                    when s7 =>
                        Contor <= Contor + 1;
                        Stare <= s8;
                    when s8 =>
                        if Contor = 8 then
                            Stare <= idle;
                        else
                            Stare <= s3;
                        end if;
                end case;
             end if;
       end if;   
    end process;
    
    TxData <= x"0B" when Stare = s1 or Stare = s2 else x"00";
    Start <= '1' when Stare = s2 or Stare = s5;
    --TxData <= x"00" when Stare = s4 or Stare = s5;
    Comanda <= '1' when Stare = s7 else '0';
    
    Reg : process(Clk)  
    begin
        if rising_edge(Clk) then
            if Comanda = '1' then
                DataInt(Contor) <= RxData;
            end if;
        end if;
    end process;
    
    Data1 <= DataInt(0) & DataInt(1) & DataInt(2) & DataInt(3);
    Data2 <= DataInt(4) & DataInt(5) & DataInt(6) & DataInt(7);
    

end Behavioral;
