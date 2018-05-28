----------------------------------------------------------------------------------
-- Company: 
-- Engineer: 
-- 
-- Create Date: 05/26/2018 06:14:21 PM
-- Design Name: 
-- Module Name: inmultitor_matriceal - Behavioral
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
use ieee.std_logic_unsigned.all;

-- Uncomment the following library declaration if using
-- arithmetic functions with Signed or Unsigned values
use IEEE.NUMERIC_STD.ALL;

-- Uncomment the following library declaration if instantiating
-- any Xilinx leaf cells in this code.
--library UNISIM;
--use UNISIM.VComponents.all;

entity inmultitor_matriceal is
  Port (X : in STD_LOGIC_VECTOR(7 downto 0);
        Y : in STD_LOGIC_VECTOR(7 downto 0);
        P : out STD_LOGIC_VECTOR(15 downto 0));
end inmultitor_matriceal;

architecture Behavioral of inmultitor_matriceal is
type MatrixP is array(0 to 7) of std_logic_vector(7 downto 0);
type MatrixSInt is array(0 to 5) of STD_LOGIC_VECTOR(7 downto 0);
signal PP : MatrixP;
signal S_int,T_int,X_Int,S,T : MatrixSint;
signal Tout : STD_LOGIC;
signal Z_Int : STD_LOGIC_VECTOR(8 downto 0);

begin

    --generarea produselor partiale
    pp_generate: for i in 0 to 7 generate
              pp_generate_col:for j in 0 to 7 generate
                                PP(i)(j) <= X(j) and Y(i);
                              end generate;  
             end generate;
             
    sst_i : entity WORK.sst 
              generic map(n => 8)
              port map(X => PP(0),
                       Y => PP(1),
                       Z => PP(2),
                       T => T(0),
                       S => S(0));
                       

    sst_gen : for i in 3 to 7 generate
                sst_i : entity WORK.sst 
                            generic map(n => 8)
                            port map(X => PP(3),
                                     Y => T(i-3),
                                     Z => S(i-3),
                                     T => T(i-3+1),
                                     S => S(i-3+1));
              end generate;  
              
    spt_gen : entity WORK.spt
                  generic map(n => 8)
                  port map(X => S(5),
                           Y => T(5),
                           Tin => '0',
                           S => P(14 downto 7),
                           Tout => Tout);  
                           
   P(0) <= S(0)(0);
   
   P_sum : for i in 0 to 5 generate
            P(i+1) <= S(i)(0);
       end generate;
              
   P(15) <= Tout;
    
                       
    
--    X_Int(0) <= '0' & PP(0);      
--    S_int(0) <= '0' & PP(1);
--    T_int(0) <= '0' & PP(2);
--    sst_i : entity WORK.sst 
--                 generic map(n => 8)
--                 port map(X => X_Int(0),
--                          Y => S_Int(0),
--                          Z => T_Int(0),
--                          T => T_Int(1),
--                          S => S_Int(1));
             
--    sst_gen : for i in 3 to 7 generate
--                X_Int(i-3+1) <= '0' & PP(i);
--                sst_i : entity WORK.sst 
--                            generic map(n => 8)
--                            port map(X => X_Int(i-3+1),
--                                     Y => S_Int(i-3+1),
--                                     Z => T_Int(i-3+1),
--                                     T => T_Int(i-3+2),
--                                     S => S_Int(i-3+2));
--              end generate;
    
--    --P(0) <= PP(0)(0);
--    p_gen : for i in 0 to 6 generate
--                P(i) <= S_Int(i)(0);
--            end generate;
              
--    spt_gen : entity WORK.spt
--                    generic map(n => 8)
--                    port map(X => S_Int(6),
--                             Y => T_Int(6),
--                             Tin => '0',
--                             S => P(15 downto 7),
--                             Tout => Tout);
    
    


end Behavioral;
