----------------------------------------------------------------------------------
-- University: TUCN 
-- Student: ldpalcu
-- 
-- Create Date: 04/10/2018 07:02:39 PM
-- Design Name: 
-- Module Name: NFP - Behavioral
-- Project Name: 
-- Target Devices: 
-- Tool Versions: 
-- Description: 
-- Neuron forward propagation

-- Dependencies: 
-- 
-- Revision:
-- Revision 0.01 - File Created
-- Additional Comments:
-- 
----------------------------------------------------------------------------------


library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
use WORK.utility.ALL;
-- Uncomment the following library declaration if using
-- arithmetic functions with Signed or Unsigned values

library ieee_proposed;
use ieee_proposed.float_pkg.all;

-- Uncomment the following library declaration if instantiating
-- any Xilinx leaf cells in this code.
--library UNISIM;
--use UNISIM.VComponents.all;

entity NFP is
    generic (previous_layer_w : natural := 3;
             nr_features      : natural := 3);
    Port ( Clk     : in STD_LOGIC;
           weights : in Vector(0 to previous_layer_w - 1);
           bias    : in float(6 downto -10);
           X       : in Vector(0 to nr_features - 1);
           Z       : out float(6 downto -10));
end NFP;

architecture Behavioral of NFP is
    signal partial_products : Vector(0 to previous_layer_w - 1);
    signal sum_final : float(6 downto -10);
    
begin
    
    
    partial : for i in 0 to previous_layer_w - 1 generate
        partial_products(i) <= weights(i)*X(i);
    end generate;
    
    --sum of partial_products   
    total_sum : process(Clk)
    variable sum : float(6 downto -10);
    begin
         sum := (others => '0');
         for i in 0 to nr_features - 1 loop
              sum := sum + partial_products(i);
         end loop;
         sum_final <= sum + bias;
    end process;
    
    Z <= sum_final;
    
     
end Behavioral;
