----------------------------------------------------------------------------------
-- Company: 
-- Engineer: 
-- 
-- Create Date: 05/26/2018 12:04:03 PM
-- Design Name: 
-- Module Name: automat_5_6_tb - Behavioral
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
use ieee.std_logic_unsigned.all;

-- Uncomment the following library declaration if using
-- arithmetic functions with Signed or Unsigned values
use IEEE.NUMERIC_STD.ALL;

-- Uncomment the following library declaration if instantiating
-- any Xilinx leaf cells in this code.
--library UNISIM;
--use UNISIM.VComponents.all;

entity automat_5_6_tb is
--  Port ( );
end automat_5_6_tb;

architecture Behavioral of automat_5_6_tb is
    signal Clk : STD_LOGIC := '0';
    signal Rst : STD_LOGIC := '0';
    signal Data : STD_LOGIC := '0';
    
    signal Flag : STD_LOGIC := '0';
    
    constant CLK_PERIOD : TIME := 10 ns;

begin
    
    DUT : entity WORK.automat_5_6
                    port map(Clk => Clk,
                            Rst => Rst,
                            Data => Data,
                            Flag => Flag);
                            
    gen_clk : process
    begin
        Clk <= '0';
        wait for (CLK_PERIOD/2);
        Clk <= '1';
        wait for (CLK_PERIOD/2);
    end process;
    
    gen_vect_test: process
    variable VecTest : STD_LOGIC_VECTOR(7 downto 0) := "11101011";
    variable RezCorect : STD_LOGIC;
    variable NrErori : INTEGER := 0;
    begin
        Rst <= '1';
        wait for CLK_PERIOD;
        Rst <= '0';
        wait for CLK_PERIOD;
        
        for i in 7 downto 0 loop
            Data <= VecTest(i);
            
            wait for CLK_PERIOD;
            
            if i > 0 then
                RezCorect := '0';
            else
                RezCorect := '1';
            end if;
            
            if (Flag/=RezCorect) then
                report  "Iteratia " & INTEGER'image(i) &
                        " Valoarea asteptata " &
                        STD_LOGIC'image(Flag) &
                        " Valoarea obtinuta " &
                        STD_LOGIC'image(RezCorect) &
                        " la momentul t = " &
                        TIME'image (now)
                        severity error;
                        
                NrErori := NrErori + 1;
             end if;
         end loop;
         if (NrErori = 0) then
                 report "Simulare terminata cu succes";
          else       
                 report "Testare terminata cu " &
                     INTEGER'image (NrErori) & " erori";
          end if;
          wait;
     end process;
    
end Behavioral;
