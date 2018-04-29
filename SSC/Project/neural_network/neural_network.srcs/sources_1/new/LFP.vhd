----------------------------------------------------------------------------------
-- University: TUCN 
-- Student: ldpalcu
-- 
-- Create Date: 04/10/2018 07:12:44 PM
-- Design Name: 
-- Module Name: LFP - Behavioral
-- Project Name: 
-- Target Devices: 
-- Tool Versions: 
-- Description: 
-- Layer for Forward Propagation
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
use IEEE.NUMERIC_STD.ALL;

-- Uncomment the following library declaration if instantiating
-- any Xilinx leaf cells in this code.
--library UNISIM;
--use UNISIM.VComponents.all;

entity LFP is
    Generic (previous_layer : natural := 3;
             next_layer     : natural := 4
             );
    Port ( Clk : in STD_LOGIC;
           Weights : in Matrix (0 to next_layer -1 , 0 to previous_layer -1);
           Bias    : in Vector (0 to next_layer - 1);
           X       : in Vector (0 to previous_layer - 1);
           Z       : out Vector (0 to next_layer - 1)
            );
end LFP;

architecture Behavioral of LFP is
    signal outputs : Vector(0 to next_layer - 1);
    
    function extract_row( m : Matrix; row : integer) return Vector is
    variable ret : Vector(0 to previous_layer-1);
    begin
    for i in 0 to previous_layer-1 loop
        ret(i) := m(row, i);
    end loop;
    
    return ret;
    end function;


begin    
    neural_units : for i in 0 to next_layer-1 generate
        signal weights_int : Vector(0 to previous_layer - 1);
    begin  
        weights_int <= extract_row(weights, i);  
        
        neural_unit : entity work.NFP 
                      generic map (previous_layer_w => previous_layer,
                                   nr_features      => previous_layer)
                      port map(
                                Clk => Clk,
                                Weights => weights_int,
                                Bias => Bias(i),
                                X => X,
                                Z => outputs(i));
    end generate neural_units;
    
    Z <= outputs;
                                

end Behavioral;
