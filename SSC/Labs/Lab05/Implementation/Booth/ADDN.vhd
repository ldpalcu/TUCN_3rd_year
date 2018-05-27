----------------------------------------------------------------------------------
-- Company: 
-- Engineer: 
-- 
-- Create Date: 03/28/2018 10:41:12 AM
-- Design Name: 
-- Module Name: ADDN - Behavioral
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

entity ADDN is
    generic (n : natural);
    Port ( X : in STD_LOGIC_VECTOR (n-1 downto 0);
           Y : in STD_LOGIC_VECTOR (n-1 downto 0);
           Tin : in STD_LOGIC;
           S : out STD_LOGIC_VECTOR (n-1 downto 0);
           Tout : out STD_LOGIC;
           OVF : out STD_LOGIC);
end ADDN;

architecture Behavioral of ADDN is
    signal sum : STD_LOGIC_VECTOR(n downto 0);
    signal TinInt: STD_LOGIC_VECTOR(n-1 downto 0):=(others => '0');
begin
     
     sum <= X + Y + (TinInt & Tin);
     S <= sum(n-1 downto 0);
     Tout <= sum(n);
     OVF <= X(n-1) xor Y(n-1);
     

end Behavioral;
