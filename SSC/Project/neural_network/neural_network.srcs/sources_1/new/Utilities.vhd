----------------------------------------------------------------------------------
-- Company: 
-- Engineer: 
-- 
-- Create Date: 04/14/2018 07:48:57 PM
-- Design Name: 
-- Module Name: Utilities - Behavioral
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

package utility is
--    constant NEURONS_INPUT_LAYER  : INTEGER := 3;
--    constant NEURONS_HIDDEN_LAYER : INTEGER := 4;
--    constant NEURONS_OUTPUT_LAYER : INTEGER := 1;
--    type MatrixWeightsIN is array (0 to NEURONS_INPUT_LAYER - 1, NEURONS_HIDDEN_LAYER - 1 downto 0) of REAL;
--    type MatrixWeightsOUT is array (0 to NEURONS_HIDDEN_LAYER - 1, NEURONS_OUTPUT_LAYER - 1 downto 0) of REAL;   
    type Vector is array(NATURAL range <>) of float(6 downto -10);
    type Matrix is array(NATURAL range <>, NATURAL range <>) of float(6 downto -10);
end package utility;


