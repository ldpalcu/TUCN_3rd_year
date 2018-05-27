----------------------------------------------------------------------------------
-- Company: 
-- Engineer: 
-- 
-- Create Date: 05/27/2018 01:03:32 PM
-- Design Name: 
-- Module Name: main_multiplier - Behavioral
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

entity main_multiplier is
  Generic(n : natural := 4);
  Port (Clk : in STD_LOGIC;
        Rst : in STD_LOGIC;
        Start : in STD_LOGIC;
        X   : in STD_LOGIC_VECTOR(n-1 downto 0);
        Y   : in STD_LOGIC_VECTOR(n-1 downto 0);
        A   : out STD_LOGIC_VECTOR(n downto 0);
        Q   : out STD_LOGIC_VECTOR(n-1 downto 0);
        Term : out STD_LOGIC
        );
end main_multiplier;

architecture Behavioral of main_multiplier is
signal Sum, TinInt, Aout : STD_LOGIC_VECTOR(n downto 0):=(others => '0');
signal Bout,Qout : STD_LOGIC_VECTOR(n-1 downto 0) := (others => '0');
signal LoadB, LoadA, ShrAQ, LoadQ,Q0, RstA : STD_LOGIC;

type TIP_STARE is (idle, init, decision, add, shift, count, stop);
signal Stare : TIP_STARE;

signal C : INTEGER := 0;

begin

    ADDN : Sum <= Bout + Aout(n-1 downto 0) + TinInt;
    
    Breg : process(Clk)
    begin
        if rising_edge(Clk) then
            if Rst = '1' then
                Bout <= (others => '0');
            elsif LoadB = '1' then
                Bout <= X;                
            end if;
         end if;
    end process;
    
    Areg : process(Clk)
    begin
        if rising_edge(Clk) then
            if RstA = '1' then
                Aout <= (others => '0');
            elsif LoadA = '1' then
                Aout <= Sum;
            elsif ShrAQ = '1' then
                Aout <= '0' & Aout(n downto 1);
            end if;
        end if;
    end process;
    
    QReg : process(Clk)
    begin
        if rising_edge(Clk) then
            if Rst = '1' then
                Qout <= (others => '0');
            elsif LOadQ = '1' then
                Qout <= Y;
            elsif ShrAQ = '1' then
                Qout <= Aout(0) & Qout(n-1 downto 1);
            end if;
        end if;
    end process;
    
    UC : process(Clk)
    begin
        if rising_edge(Clk) then
            if Rst = '1' then
                Stare <= idle;
            else
                case Stare is
                    when idle =>
                        if Start = '1' then
                            Stare <= init;
                        end if;
                    when init =>
                        C <= n - 1;
                        Stare <= decision;
                    when decision =>
                        if Q0 = '1' then
                            Stare <= add;
                        else
                            Stare <= shift;
                        end if;
                    when add =>
                        Stare <= shift;
                    when shift =>
                        Stare <= count;
                    when count =>
                        C <= C - 1;
                        if C = 0 then
                            Stare <= stop;
                        else
                            Stare <= decision;
                        end if;
                    when stop =>
                        Stare <= stop;
                end case;
             end if;
        end if;
   end process;
   
   LoadB <= '1' when Stare = init else '0';
   LoadA <= '1' when Stare = add else '0';
   LoadQ <= '1' when Stare = init else '0';
   ShrAQ <= '1' when Stare = shift else '0';
   RstA  <= '1' when Stare = init else '0';
   Term  <= '1' when Stare = stop else '0';
   
   A <= Aout;
   Q <= Qout;
   
   Q0 <= Qout(0);
    
end Behavioral;
