----------------------------------------------------------------------------------
-- Company: 
-- Engineer: 
-- 
-- Create Date: 03/21/2018 10:17:03 AM
-- Design Name: 
-- Module Name: sum_2 - Behavioral
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

entity sum_2 is
    Port ( X : in STD_LOGIC_VECTOR (1 downto 0);
           Y : in STD_LOGIC_VECTOR (1 downto 0);
           Tin : in STD_LOGIC;
           S : out STD_LOGIC_VECTOR (1 downto 0);
           P : out STD_LOGIC;
           G : out STD_LOGIC);
end sum_2;

architecture Behavioral of sum_2 is
    signal p1, p0, g1, g0 : STD_LOGIC;
    signal Taux : STD_LOGIC;
begin
    S(0) <= X(0) xor Y(0) xor Tin;
    Taux <= (X(0) AND Y(0)) OR ((X(0) OR Y(0)) AND Tin);
    S(1) <= X(1) xor Y(1) xor Taux;
    
    p0 <= X(0) or Y(0);
    p1 <= X(1) or Y(1);
    g1 <= X(1) and Y(1);
    g0 <= X(0) and Y(0);
    P <= p1 and p0;
    G <= g1 or (p1 and g0);
    
end Behavioral;
