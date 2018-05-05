----------------------------------------------------------------------------------
-- Company: 
-- Engineer: 
-- 
-- Create Date: 04/29/2018 06:23:19 PM
-- Design Name: 
-- Module Name: DAFHU - Behavioral
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

library ieee_proposed;
use ieee_proposed.float_pkg.all;

use WORK.utility.all;

-- Uncomment the following library declaration if using
-- arithmetic functions with Signed or Unsigned values
--use IEEE.NUMERIC_STD.ALL;

-- Uncomment the following library declaration if instantiating
-- any Xilinx leaf cells in this code.
--library UNISIM;
--use UNISIM.VComponents.all;

entity DAFHU is
    Generic( n : natural);
    Port ( Clk : in STD_LOGIC;
           A   : in Vector(0 to n - 1);
           dA  : out Vector(0 to n - 1));
end DAFHU;

architecture Behavioral of DAFHU is

begin

    dActivation : for i in 0 to n - 1 generate
                dA(i) <= to_float(1,A(i)) when A(i) > 0 else to_float(0,A(i));
    end generate;


end Behavioral;
