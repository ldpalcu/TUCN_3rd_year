----------------------------------------------------------------------------------
-- Company: 
-- Engineer: 
-- 
-- Create Date: 03/21/2018 10:28:05 AM
-- Design Name: 
-- Module Name: sum_8 - Behavioral
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

entity sum_8 is
    Port ( X : in STD_LOGIC_VECTOR (7 downto 0);
           Y : in STD_LOGIC_VECTOR (7 downto 0);
           Tin : in STD_LOGIC;
           S : out STD_LOGIC_VECTOR (7 downto 0);
           Tout : out STD_LOGIC);
end sum_8;

architecture Behavioral of sum_8 is
    signal G01, G23, G45, G67 : STD_LOGIC;
    signal P01, P23, P45, P67 : STD_LOGIC;
    signal S01, S23, S45, S67 : STD_LOGIC_VECTOR(1 downto 0);
    signal T2, T4, T6 : STD_LOGIC;
begin
    sum_1: entity WORK.sum_2 port map (
                X => X(1 downto 0),
                Y => Y(1 downto 0),
                Tin => Tin,
                S => S01,
                P => P01,
                G => G01);
    sum_2: entity WORK.sum_2 port map (
                X => X(3 downto 2),
                Y => Y(3 downto 2),
                Tin => Tin,
                S => S23,
                P => P23,
                G => G23);
    sum_3: entity WORK.sum_2 port map (
                X => X(5 downto 4),
                Y => Y(5 downto 4),
                Tin => Tin,
                S => S45,
                P => P45,
                G => G45);
    sum_4: entity WORK.sum_2 port map (
                X => X(7 downto 6),
                Y => Y(7 downto 6),
                Tin => Tin,
                S => S67,
                P => P67,
                G => G67);              
     T2 <= G01 or (P01 and Tin);
     T4 <= G23 or (P23 AND G01) OR (P23 AND P01 AND Tin);
     T6 <= G45 OR (P45 AND G23) OR (P45 AND P23 AND G01) OR (P45 AND P23 AND P01 AND Tin);
     Tout <= G67 OR (P67 AND G45) OR (P67 AND P45 AND G23) OR (P67 AND P45 AND P23 AND G01) OR 
            (P67 AND P45 AND P23 AND P01 AND Tin);
     S <= S67 & S45 & S23 & S01;
                
end Behavioral;
