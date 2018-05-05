----------------------------------------------------------------------------------
-- University: UTCN 
-- Student: ldpalcu
-- 
-- Create Date: 04/10/2018 06:53:07 PM
-- Design Name: 
-- Module Name: BP - Behavioral
-- Project Name: Neural Network
-- Target Devices: 
-- Tool Versions: 
-- Description: 
-- Backpropagation algorithm
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

entity BP is
    Generic(previous_layer : natural := 3;
            next_layer     : natural := 4);
    Port ( Clk : in STD_LOGIC;
           Alpha : in float(6 downto -10);
           output : in float(6 downto -10);
           expected_output : in float(6 downto -10);
           A1  : in Vector(0 to next_layer - 1);
           Z1  : in Vector(0 to next_layer - 1);
           X   : in Vector(0 to previous_layer - 1);
           B_old_1  : in Vector(0 to next_layer - 1);
           B_old_2 : in float(6 downto -10);
           W_old_1  : in Matrix(0 to next_layer - 1, 0 to previous_layer - 1);
           W_old_2 : in Vector(0 to next_layer - 1);
           W_new_1  : out Matrix(0 to next_layer - 1, 0 to previous_layer - 1);
           W_new_2 : out Vector(0 to next_layer - 1);
           B_new_1  : out Vector(0 to next_layer - 1);
           B_new_2 : out float(6 downto -10)
           );
end BP;

architecture Behavioral of BP is
    signal dW1  :  Matrix(0 to next_layer - 1, 0 to previous_layer - 1);
    signal dB1  :  Vector(0 to next_layer - 1);
    signal dW2  :  Vector(0 to next_layer - 1);
    signal dB2  :  float(6 downto -10);
    signal dZ2  :  float(6 downto -10);
    signal dA   :  Vector(0 to next_layer - 1);
    
    function extract_row( m : Matrix; row : integer) return Vector is
        variable ret : Vector(0 to previous_layer-1);
        begin
        for i in 0 to previous_layer-1 loop
            ret(i) := m(row, i);
        end loop;
        
        return ret;
        end function;
    
begin
    
    output_layer_bp: entity WORK.NBP
                            generic map(nr_weights => next_layer)
                            port map(
                                    Clk => Clk,  
                                    output => output, 
                                    expected_output => expected_output,
                                    A  => A1,  
                                    dZ => dZ2, 
                                    dW => dW2,
                                    dB => dB2
                                    );
                                    
     weights_2 : for i in 0 to next_layer - 1 generate
                       W_new_2(i) <= W_old_2(i) - Alpha * dW2(i);
                 end generate;
                 
     B_new_2 <= B_old_2 - Alpha * dB2;
     
     dActivation : entity WORK.DAFHU 
                          generic map (n => next_layer)
                          port map(Clk => Clk,
                                   A => A1,
                                   dA => dA);

     hidden_layer_bp : entity WORK.LBP 
                            generic map(nr_previous_layer => previous_layer,
                                        nr_next_layer     => next_layer )
                            port map(
                                    Clk => Clk,
                                    Z1  => Z1,
                                    X   => X,
                                    W2  => W_old_2,
                                    Z2  => dZ2,
                                    dA  => dA,
                                    dW  => dW1,
                                    dB  => dB1
                                    );
                                    
     -- update weights
     
     weights_1_update : for i in 0 to next_layer - 1 generate
     
--     signal weightsInt : Vector (0 to previous_layer - 1);
--     signal dWeightsInt : Vector (0 to previous_layer - 1);
     
     begin
--        weightsInt <= extract_row(W_old_1, i);
--        dWeightsInt <= extract_row(dW1, i);
        
        weights_1 : for j in 0 to previous_layer - 1 generate
        begin
            W_new_1(i,j) <= W_old_1(i,j) - Alpha * dW1(i,j);
        end generate;
     end generate;
     
     bias_1_update : for i in 0 to next_layer - 1 generate
     begin
          B_new_1(i) <= B_old_1(i) - Alpha * dB1(i);
     end generate;


end Behavioral;
