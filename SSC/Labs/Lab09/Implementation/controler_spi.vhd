----------------------------------------------------------------------------------
-- Company: 
-- Engineer: 
-- 
-- Create Date: 04/25/2018 10:27:05 AM
-- Design Name: 
-- Module Name: controler_spi - Behavioral
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

entity controler_spi is
    Generic (frecv_SCLK : INTEGER := 5_000_000;
             Dim_Cuv   : INTEGER := 8);
             
    Port ( Clk : in STD_LOGIC;
           Rst : in STD_LOGIC;
           Start : in STD_LOGIC;
           TxData : in STD_LOGIC_VECTOR (Dim_Cuv-1 downto 0);
           MISO : in STD_LOGIC;
           MOSI : out STD_LOGIC;
           SCLK : out STD_LOGIC;
           SS : out STD_LOGIC;
           TxRdy : out STD_LOGIC;
           RxRdy : out STD_LOGIC;
           RxData : out STD_LOGIC_VECTOR (Dim_Cuv-1 downto 0));
end controler_spi;

architecture Behavioral of controler_spi is
    constant frecv_Clk : INTEGER := 100_000_000;
    constant frecv_div : INTEGER := frecv_Clk / frecv_SCLK;
    signal Start_Reg, Start_int : STD_LOGIC := '0';
    signal RstStart, LdTxRx, ShTxRx : STD_LOGIC := '0';
    signal SCLK_int, SclkEn,Sin : STD_LOGIC := '0';
    signal CE_p, CE_n : STD_LOGIC := '0';
    signal Q_TxData_reg, Q_TxRx_reg : STD_LOGIC_VECTOR (Dim_Cuv-1 downto 0) := (others => '0');
    
    type TIP_STARE is (idle,load,tx_rx,bit0,ready);
    signal Stare : TIP_STARE;
    
begin

    Gen_Sclk : process(Clk)
        variable contor : INTEGER := 0;
        variable flag : INTEGER := 0;
    begin
        if RISING_EDGE(Clk) then
            CE_P <= '0';
            CE_n <= '0';
            if contor = frecv_div/2 then
                SCLK_int <= not SCLK_int;
                contor := 0;
                CE_p <= '1';
                CE_n <= '1';
            end if;           
            contor := contor + 1;
        end if;
    end process;
    
    Bist_D : process(Clk, RstStart)
    begin
        if RISING_EDGE(Clk) then
            if RstStart = '1' then
                Start_int <= '0';
            else
                Start_int <= Start_reg;
            end if;
        end if;
    end process;
    
    TxData_reg : process(Clk)
    begin
        if rising_edge(Clk) then
            if Start = '1' then
                Q_TxData_reg <= TxData;
            end if;
        end if;
    end process;
    
    TxRx_reg : process(Clk)
    begin
        if rising_edge(Clk) then
            if CE_n = '1' then
                if Rst = '1' then
                    Q_TxRx_reg <= (others => '0');
                elsif LdTxRx = '1' then
                    Q_TxRx_reg <= Q_TxData_reg;
                elsif ShTxRx = '1' then
                    Q_TxRx_reg <= Q_TxData_reg(Dim_Cuv - 2 downto 0) & Sin;
                    MOSI <= Q_TxRx_reg(Dim_Cuv - 1);
                end if;
           end if;
       end if;
        
    end process;
    
    RxData <= Q_TxRx_reg;
    
    Rx_reg : process(Clk)
    begin
        if rising_edge(Clk) then
            if CE_p = '1' then
                if Rst = '1' then
                    Sin <= '0';
                else
                    Sin <= MISO;
                end if;
            end if;
       end if;
    end process;
    
    SCLK_P : process(Clk)
    begin
        if SclkEn = '1' then
            SCLK <= Sclk_int;
        end if;
    end process;
    
    UC : process(Clk)
    variable CntBit :INTEGER := 0;
    begin
        if rising_edge(Clk) then
            if CE_n = '1' then
                if Rst = '1' then
                    Stare <= idle;
                else
                    case Stare is
                        when idle =>
                            if Start_int = '1' then
                                CntBit := Dim_Cuv - 1;
                                Stare <= load;
                            end if;
                        when load =>
                            Stare <= tx_rx;
                        when tx_rx =>
                            if CntBit = 0 then
                                Stare <= bit0;
                            else
                                CntBit:=CntBit - 1;
                            end if;
                        when bit0 =>
                            Stare <= ready;
                        when ready  =>
                            CntBit := Dim_Cuv - 1;
                            if Start_int = '0' then
                                Stare <= idle;
                            else
                                Stare <= load;
                            end if;
                     end case;
                 end if;
             end if;
        end if;
                     
    end process;
    
    LdTxRx <= '1' when Stare = load else '0';
    SS <= '1' when Stare = idle else '0';
    RstStart <= '1' when Stare = load else '0';
    ShTxRx <= '1' when Stare = tx_rx else '0';
    SclkEn <= '1' when Stare = tx_rx else '0';
    TxRdy <= '1' when Stare = bit0 else '0';
    RxRdy <= '1' when Stare = ready else '0';  
    

end Behavioral;
