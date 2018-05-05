----------------------------------------------------------------------------------
-- Company: 
-- Engineer: 
-- 
-- Create Date: 04/29/2018 07:56:29 PM
-- Design Name: 
-- Module Name: HNBP - Behavioral
-- Project Name: 
-- Target Devices: 
-- Tool Versions: 
-- Description: 
-- Hidden Unit Neuron from Backward Propagation Layer
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

entity HNBP is
    Generic(
            nr_features : natural);
    Port ( Clk : in STD_LOGIC;
           dZ : in float(6 downto -10);
           w2 : in float(6 downto -10);
           dA : in float(6 downto -10);
           X  : in Vector(0 to nr_features - 1);
           dZout : out float(6 downto -10);
           dW : out Vector(0 to nr_features - 1);
           dB : out float(6 downto -10));
end HNBP;

architecture Behavioral of HNBP is
    signal dZ_l1 : float(6 downto -10);

begin
    
--    z : process(Clk)
--    variable sum : float(8 downto -10);
--    begin
--        sum := (others => '0');
--        for i in 0 to nr_weights - 1 loop
--            sum := sum + weights(i);
--        end loop;
--        dZprev <= sum * dZ * dA;    
--    end process;


    dZ_l1 <= w2 * dZ * dA;
    
    dWeights : for i in 0 to nr_features - 1 generate
                   dW(i) <= dZ_l1 * X(i);
               end generate;
               
    --not sure if it is correct
    --check the formula
   dB <= dZ_l1;
   
   dZout <= dZ_l1;

end Behavioral;
