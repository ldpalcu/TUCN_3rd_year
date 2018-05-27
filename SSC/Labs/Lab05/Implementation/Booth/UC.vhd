----------------------------------------------------------------------------------
-- Company: 
-- Engineer: 
-- 
-- Create Date: 03/28/2018 11:11:45 AM
-- Design Name: 
-- Module Name: UC - Behavioral
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
--use IEEE.NUMERIC_STD.ALL;

-- Uncomment the following library declaration if instantiating
-- any Xilinx leaf cells in this code.
--library UNISIM;
--use UNISIM.VComponents.all;

entity UC is
    generic (n : natural);
    Port ( Start : in STD_LOGIC;
           Clk   : in STD_LOGIC;
           Rst   : in STD_LOGIC;
           Q0Qm1 : in STD_LOGIC_VECTOR (1 downto 0);
           Term  : out STD_LOGIC;
           LoadA : out STD_LOGIC;
           LoadB : out STD_LOGIC;
           RstA  : out STD_LOGIC;
           SubB  : out STD_LOGIC;
           ShrAQ : out STD_LOGIC;
           LoadQ : out STD_LOGIC;
           RstQm1 : out STD_LOGIC);
end UC;

architecture Behavioral of UC is
    type TIP_STARE is (idle, init, decision, add, sub, shift, count, stop);
    signal Stare : TIP_STARE;
    signal c : INTEGER;   

begin
    proc1 : process(Clk)
    begin
        if RISING_EDGE(Clk) then
            if Rst = '1' then
                Stare <= idle;
            else
                case Stare is
                    when idle =>
                        if Start = '1' then
                             Stare <= init;
                        end if;
                    when init =>
                        c <= n - 1;
                        Stare <= decision;
                    when decision =>
                        case Q0Qm1 is
                            when "00" =>
                                Stare <= shift;
                            when "01" =>
                                Stare <= add;
                            when "10" =>
                                Stare <= sub;
                            when "11" =>
                                Stare <= shift;
                            when others =>
                                Stare <= idle;
                        end case;
                    when add =>
                        Stare <= shift;
                    when sub =>
                        Stare <= shift;
                    when shift =>
                        Stare <= count;
                    when count =>
                        c <= c - 1;
                        if c = 0 then
                            Stare <= stop;
                        else
                            Stare <= decision;
                        end if;
                    when stop =>
                        Stare <= stop;
               end case;
           end if;
        end if;
    end process proc1;
    
--    LoadB  <= '0';
--    LoadQ  <= '0';
--    RstQm1 <= '0';
--    RstA   <= '0';
--    LoadA  <= '0';
--    ShrAQ  <= '0';
--    SubB   <= '0';
--    Term   <= '0';
    
    proc2: process(Stare)
    begin
        case Stare is
            when idle => LoadB <='0'; LoadQ <='0'; RstQm1 <='0'; RstA <='0'; ShrAQ <='0'; SubB <='0'; Term <= '0';
            when init => LoadB <='1'; LoadQ <='1'; RstQm1 <='1'; RstA <='1'; ShrAQ <='0'; SubB <='0'; Term <= '0';
            when decision => RstQm1 <= '0'; LoadB <= '0'; RstA <= '0'; LoadQ <= '0'; LoadA <= '0'; ShrAQ <= '0'; SubB <= '0'; Term <= '0';
            when add => RstQm1 <= '0'; LoadB <= '0'; RstA <= '0'; LoadQ <= '0'; LoadA <= '1'; ShrAQ <= '0'; SubB <= '0'; Term <= '0';
            when sub => SubB <= '1'; RstQm1 <= '0'; LoadB <= '0'; RstA <= '0'; LoadQ <= '0'; LoadA <= '1'; ShrAQ <= '0'; Term <= '0';
            when shift => ShrAQ <= '1'; RstQm1 <= '0'; LoadB <= '0'; RstA <= '0'; LoadQ <= '0'; LoadA <= '0'; SubB <= '0'; Term <= '0'; 
            when count => RstQm1 <= '0'; LoadB <= '0'; RstA <= '0'; LoadQ <= '0'; LoadA <= '0'; ShrAQ <= '0'; SubB <= '0'; Term <= '0';
            when stop => Term <= '1'; RstQm1 <= '0'; LoadB <= '0'; RstA <= '0'; LoadQ <= '0'; LoadA <= '0'; ShrAQ <= '0'; SubB <= '0';
        end case;
    end process proc2;
                             


end Behavioral;
