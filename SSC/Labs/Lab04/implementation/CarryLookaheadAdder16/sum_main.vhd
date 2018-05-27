----------------------------------------------------------------------------------
-- Company: 
-- Engineer: 
-- 
-- Create Date: 05/26/2018 03:06:29 PM
-- Design Name: 
-- Module Name: sum_main - Behavioral
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

entity sum_main is
  Port (X : in STD_LOGIC_VECTOR(15 downto 0);
        Y : in STD_LOGIC_VECTOR(15 downto 0);
        Tin : in STD_LOGIC;
        S : out STD_LOGIC_VECTOR(15 downto 0);
        Tout : out STD_LOGIC );
end sum_main;

architecture Behavioral of sum_main is
    signal P,G : STD_LOGIC_VECTOR(3 downto 0);
    signal T : STD_LOGIC_VECTOR(4 downto 0);
begin
    
    T(0) <= Tin;
    sum_gen : for i in 0 to 15 generate
                sum_gen_if: if i mod 4 = 0 generate 
                                sum_i : entity WORK.sum_4 
                                            port map (X => X(i+3 downto i),
                                                      Y => Y(i+3 downto i),
                                                      Tin => T(i/4),
                                                      S => S(i+3 downto i),
                                                      P => P(i/4),
                                                      G => G(i/4));
                              end generate;
              end generate;
    
    t_gen : for i in 1 to 4 generate
                T(i) <= G(i-1) or (P(i-1) and T(i-1));
            end generate;                                 
                                                            
    Tout <= T(4);

end Behavioral;
