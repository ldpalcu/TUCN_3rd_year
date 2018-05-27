----------------------------------------------------------------------------------
-- Company: 
-- Engineer: 
-- 
-- Create Date: 05/23/2018 10:29:52 AM
-- Design Name: 
-- Module Name: InmMatriceala - Behavioral
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

entity InmMatriceala is
  Port ( 
    X: in std_logic_vector(7 downto 0);
    Y: in std_logic_vector(7 downto 0);
    P: out std_logic_vector(15 downto 0)
  );
end InmMatriceala;

architecture Behavioral of InmMatriceala is

type Matrix is array(0 to 7) of std_logic_vector(8 downto 0);
type MatrixP is array(0 to 7) of std_logic_vector(7 downto 0);
type MatrixT is array(0 to 6) of std_logic_vector(8 downto 0);
signal S : Matrix;
signal PP : MatrixP;
signal T : MatrixT;

begin

pp_generate: for i in 0 to 7 generate
              pp_generate_col:for j in 0 to 7 generate
                                PP(i)(j) <= X(j) and Y(i);
                              end generate;  
             end generate;
         
trans_generate: for i in 0 to 6 generate
                 T(i)(0) <= '0';
                end generate;
                
init_sum_f: S(0)(8) <= '0';

init_sum: for i in 1 to 7 generate
           S(0)(i) <= PP(0)(i);
          end generate;

init_sum_col: for i in 1 to 7 generate
               S(i)(8) <= T(i-1)(8);
              end generate;

S(0)(0) <= PP(0)(0);

inmultire: for i in 0 to 6 generate
           inml_col: for j in 0 to 7 generate
                      sum: entity WORK.SE port map (
                             x => S(i)(j+1),
                             y => PP(i+1)(j),
                             tin => T(i)(j),
                             s => S(i+1)(j),
                             tout => T(i)(j+1)
                         );
                     end generate;
           end generate;

P(0) <= X(0) and Y(0);

prod_6: for i in 1 to 6 generate
         P(i) <= S(i)(0);
        end generate;             
        
prod_final: for i in 7 to 15 generate
             P(i) <= S(7)(i-7);
            end generate;

end Behavioral;
