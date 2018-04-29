----------------------------------------------------------------------------------
-- University: TUCN 
-- Student: ldpalcu
-- 
-- Create Date: 04/10/2018 06:39:02 PM
-- Design Name: 
-- Module Name: FP - Behavioral
-- Project Name: 
-- Target Devices: 
-- Tool Versions: 
-- Description: 
-- Forward propagation algorithm
-- Dependencies: 
-- 
-- Revision:
-- Revision 0.01 - File Created
-- Additional Comments:
-- 
----------------------------------------------------------------------------------


library IEEE;
use IEEE.STD_LOGIC_1164.ALL;

use WORK.utility.all;

library ieee_proposed;
use ieee_proposed.float_pkg.all;

-- Uncomment the following library declaration if using
-- arithmetic functions with Signed or Unsigned values
--use IEEE.NUMERIC_STD.ALL;

-- Uncomment the following library declaration if instantiating
-- any Xilinx leaf cells in this code.
--library UNISIM;
--use UNISIM.VComponents.all;

entity FP is
    Generic ( nr_features : natural := 3;
              next_layer  : natural := 4);
    Port ( Clk : in STD_LOGIC;
           X   : in Vector ( 0 to nr_features - 1);
           Weights_hidden_layer : in Matrix(0 to next_layer -1,0 to nr_features - 1);
           Bias : in Vector(0 to next_layer - 1);
           Bias_output : in float(6 downto -10);
           Weights_output_layer : in Vector(0 to next_layer -1);
           Z_final : out float(6 downto -10));
end FP;

architecture Behavioral of FP is
    signal Z_int : Vector(0 to next_layer - 1); 
    signal A_int : Vector(0 to next_layer - 1);
    signal Z_output : float(6 downto -10);

begin

    hidden_layer: entity WORK.LFP 
                  generic map (previous_layer => nr_features,
                               next_layer     => next_layer)
                  port map (Clk  => Clk,
                            Weights => Weights_hidden_layer,
                            Bias => Bias,
                            X    => X,
                            Z    => Z_int);
                            
    af : for i in 0 to next_layer - 1 generate
        activation_function : entity WORK.AFHU 
                              port map(Clk => Clk,
                                        Z => Z_int(i),
                                        A => A_int(i));
    end generate;
    
    output_unit : entity WORK.NFP
                  generic map (previous_layer_w => next_layer,
                               nr_features => next_layer)
                  port map (Clk => Clk,
                            weights => weights_output_layer,
                            Bias    => Bias_output,
                            X       => A_int,
                            Z       => Z_output);
                            
    Z_final <= Z_output;
                      

end Behavioral;
