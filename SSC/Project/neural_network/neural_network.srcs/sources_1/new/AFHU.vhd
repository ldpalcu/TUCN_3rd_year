----------------------------------------------------------------------------------
-- University: TUCN 
-- Student: ldpalcu
-- 
-- Create Date: 04/10/2018 07:04:01 PM
-- Design Name: 
-- Module Name: AFHU - Behavioral
-- Project Name: 
-- Target Devices: 
-- Tool Versions: 
-- Description: 
-- Activation Function for Hidden Units (Neurons from Hidden Layer)
-- Dependencies: 
-- 
-- Revision:
-- Revision 0.01 - File Created
-- Additional Comments:
-- 
----------------------------------------------------------------------------------


library IEEE;
use IEEE.STD_LOGIC_1164.ALL;

library IEEE_proposed;
use ieee_proposed.float_pkg.all;

-- Uncomment the following library declaration if using
-- arithmetic functions with Signed or Unsigned values
--use IEEE.NUMERIC_STD.ALL;

-- Uncomment the following library declaration if instantiating
-- any Xilinx leaf cells in this code.
--library UNISIM;
--use UNISIM.VComponents.all;

entity AFHU is
    Port ( Clk : in STD_LOGIC;
           Z : in float(6 downto -10);
           A : out float(6 downto -10));
end AFHU;

architecture Behavioral of AFHU is

begin
    
    A <= Z when Z > to_float(0, Z) else to_float(0, Z);
    
end Behavioral;
