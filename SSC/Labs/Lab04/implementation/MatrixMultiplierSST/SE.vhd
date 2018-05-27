----------------------------------------------------------------------------------
-- Company: 
-- Engineer: 
-- 
-- Create Date: 05/26/2018 06:04:09 PM
-- Design Name: 
-- Module Name: SE - Behavioral
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

entity SE is
  Port (X : in STD_LOGIC;
        Y : in STD_LOGIC;
        Tin : in STD_LOGIC;
        S : out STD_LOGIC;
        Tout : out STD_LOGIC);
end SE;

architecture Behavioral of SE is

begin
    S <= X xor Y xor Tin;
    Tout <= (X and Y) Or ((X or Y) and Tin);

end Behavioral;
