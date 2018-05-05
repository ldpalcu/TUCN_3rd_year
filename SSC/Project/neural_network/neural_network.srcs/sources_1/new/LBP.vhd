----------------------------------------------------------------------------------
-- Company: 
-- Engineer: 
-- 
-- Create Date: 04/29/2018 05:05:53 PM
-- Design Name: 
-- Module Name: LBP - Behavioral
-- Project Name: 
-- Target Devices: 
-- Tool Versions: 
-- Description: 
-- Hidden Layer for BackProp 
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

entity LBP is
    Generic ( nr_previous_layer : natural;
              nr_next_layer : natural);
    Port ( Clk : in STD_LOGIC;
           Z1  : in Vector(0 to nr_next_layer - 1);
           X   : in Vector(0 to nr_previous_layer - 1);
           W2  : in Vector(0 to nr_next_layer - 1);
           Z2  : in float(6 downto -10);
           dA  : in Vector(0 to nr_next_layer - 1);
           dW  : out Matrix(0 to nr_next_layer - 1, 0 to nr_previous_layer - 1);
           dB  : out Vector(0 to nr_next_layer - 1)
           );
end LBP;

architecture Behavioral of LBP is
    signal outputs : Vector(0 to nr_next_layer - 1);
    signal dZ : Vector(0 to nr_next_layer - 1);
    
    function extract_row( m : Matrix; row : integer) return Vector is
    variable ret : Vector(0 to nr_previous_layer-1);
    begin
    for i in 0 to nr_previous_layer-1 loop
        ret(i) := m(row, i);
    end loop;
    
    return ret;
    end function;
    
    function add_row( m : Matrix; row : integer) return Vector is
        variable ret : Vector(0 to nr_previous_layer-1);
        begin
        for i in 0 to nr_previous_layer-1 loop
            ret(i) := m(row, i);
        end loop;
        
        return ret;
        end function;

begin
    
    hidden_units : for i in 0 to nr_next_layer - 1 generate
 
    signal dweights_int : Vector(0 to nr_previous_layer - 1);
    
    begin
            
            hidden_unit : entity WORK.HNBP 
                            generic map (nr_features => nr_previous_layer)
                            port map(
                                Clk => Clk,
                                dZ  => Z2,
                                w2 => W2(i),
                                dA => dA(i),
                                X  => X,
                                dZout => dZ(i),
                                dW => dweights_int,
                                dB => dB(i));
                                
            add_row : for j in 0 to nr_previous_layer - 1 generate
                      dW(i, j) <= dweights_int(j);
            end generate;
            
    end generate;
    
    --update weights


end Behavioral;
