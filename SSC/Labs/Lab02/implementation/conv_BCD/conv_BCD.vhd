----------------------------------------------------------------------------------
-- Company: 
-- Engineer: 
-- 
-- Create Date: 03/14/2018 07:30:34 AM
-- Design Name: 
-- Module Name: conv_BCD - Behavioral
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

entity conv_BCD is
    Port ( bin_number : in STD_LOGIC_VECTOR (3 downto 0);
           BCD1 : out STD_LOGIC_VECTOR (3 downto 0);
           BCD0 : out STD_LOGIC_VECTOR (3 downto 0));
end conv_BCD;

architecture Behavioral of conv_BCD is
    
begin
    BCD1 <= x"0" when bin_number <= x"9" and bin_number >= x"0" else
            x"1" ;
    BCD0 <= x"0" when bin_number = x"0" or bin_number = x"A" else
            x"1" when bin_number = x"1" or bin_number = x"B" else
            x"2" when bin_number = x"2" or bin_number = x"C" else
            x"3" when bin_number = x"3" or bin_number = x"D" else
            x"4" when bin_number = x"4" or bin_number = x"E" else
            x"5" when bin_number = x"5" or bin_number = x"F" else
            x"6" when bin_number = x"6" else
            x"7" when bin_number = x"7" else
            x"8" when bin_number = x"8" else
            x"9";
end Behavioral;
