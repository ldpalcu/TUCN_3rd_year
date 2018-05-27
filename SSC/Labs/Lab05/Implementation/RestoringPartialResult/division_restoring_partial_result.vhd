----------------------------------------------------------------------------------
-- Company: 
-- Engineer: 
-- 
-- Create Date: 05/27/2018 02:25:41 PM
-- Design Name: 
-- Module Name: division_restoring_partial_result - Behavioral
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

entity division_restoring_partial_result is
  Generic(nd : natural := 16;
          ni : natural := 8);
  Port ( Clk   : in STD_LOGIC;
         Rst   : in STD_LOGIC;
         Start : in STD_LOGIC;
         X     : in STD_LOGIC_VECTOR(nd-1 downto 0);
         Y     : in STD_LOGIC_VECTOR(ni-1 downto 0);
         A_Q   : out STD_LOGIC_VECTOR(nd downto 0);
         Term  : out STD_LOGIC);
end division_restoring_partial_result;

architecture Behavioral of division_restoring_partial_result is
signal Sum, Aout  : STD_LOGIC_VECTOR(ni downto 0) := (others => '0');
signal Tint, Bxor, Bout,Qout, Txor : STD_LOGIC_VECTOR(ni-1 downto 0) := (others => '0');
signal Tin, LoadB, LoadA, LoadQ, ShlAQ, SubB, Q0: STD_LOGIC;

type TIP_STARE is (idle, init, shift, sub, decision, add, count, stop);
signal Stare : TIP_STARE;

signal C : INTEGER := 0;
begin
    
    ADDN : Sum <= Aout + Bxor + (Tint & Tin);
    
    Breg : process(Clk)
    begin
        if rising_edge(Clk) then
            if Rst = '1' then
                Bout <= (others => '0');
            elsif LoadB = '1' then
                Bout <= Y;
            end if;
        end if;
    end process;
    
    Areg : process(Clk)
    begin
        if rising_edge(Clk) then
            if Rst = '1' then
                Aout <= (others => '0');
            elsif LoadQ = '1' then 
                Aout <= '0' & X(nd-1 downto nd/2);
            elsif LoadA = '1' then
                Aout <= Sum;
            elsif ShlAQ = '1' then
                Aout <= Aout(ni-1 downto 0) & Qout(ni-1);
            end if;
        end if;
     end process;
     
     Qreg : process(Clk)
     begin
        if rising_edge(Clk) then
            if Rst = '1' then
                Qout <= (others => '0');
            elsif LoadQ = '1' then
                Qout <= X(nd/2-1 downto 0);
            elsif ShlAQ = '1' then
                Qout <= Qout(ni-2 downto 0) & Q0;
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
                        C <= ni - 1;
                        Stare <= shift;
                    when shift =>
                        Stare <= sub;
                    when sub =>
                        Stare <= decision;
                    when decision =>
                        if Aout(ni) = '1' then
                            Stare <= add;
                        else
                            Stare <= count;
                        end if;                           
                    when add =>
                        Stare <= count;
                    when count =>
                        C <= C - 1;
                        if C = 0 then
                            Stare <= stop;
                        else 
                            Stare <= shift;
                        end if;
                    when stop => 
                        Stare <= stop;
                end case;
            end if;
        end if;
     end process;
     
     LoadA <= '1' when (Stare = init or Stare = sub or Stare = add) else '0';
     LoadB <= '1' when Stare = init else '0';
     LoadQ <= '1' when Stare = init else '0';
     ShlAQ <= '1' when Stare = shift else '0';
     SubB <= '1' when Stare = sub else '0';
     Term <= '1' when Stare = stop else '0';
     Q0 <= not Aout(ni) when Stare = decision else Qout(0);
     
     Tin <= SubB;
     Bxor <= Bout xor Txor;               
     Txor <= (others => SubB); 
     
     A_Q <= Aout & Qout;         
        
     
            


end Behavioral;
