----------------------------------------------------------------------------------
-- Company: 
-- Engineer: 
-- 
-- Create Date: 04/29/2018 05:05:31 PM
-- Design Name: 
-- Module Name: NBP - Behavioral
-- Project Name: 
-- Target Devices: 
-- Tool Versions: 
-- Description: 
-- Output Unit Neuron from Backward Propagation Layer
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

entity NBP is
    Generic(nr_weights : natural);
    Port ( Clk : in STD_LOGIC;
           output : in float(6 downto -10);
           expected_output : in float(6 downto -10);
           A  : in Vector(0 to nr_weights - 1);
           dZ : out float(6 downto -10);
           dW : out Vector (0 to nr_weights - 1);
           dB : out float(6 downto -10)
           );
end NBP;

architecture Behavioral of NBP is
    signal dZint : float(6 downto -10);

begin
    dZint <= output - expected_output;
    
    dWeights : for i in 0 to nr_weights-1 generate
                    dW(i) <= dZint * A(i);
               end generate;
    
    dB <= dZint;
    
    dZ <= dZint;
   
end Behavioral;
