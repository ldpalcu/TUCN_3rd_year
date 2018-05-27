----------------------------------------------------------------------------------
-- Company: 
-- Engineer: 
-- 
-- Create Date: 05/26/2018 07:42:45 PM
-- Design Name: 
-- Module Name: spt - Behavioral
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

entity spt is
  Generic(n : natural);
  Port (X    : in STD_LOGIC_VECTOR(n downto 0);
        Y    : in STD_LOGIC_VECTOR(n downto 0);
        Tin  : in STD_LOGIC;
        S    : out STD_LOGIC_VECTOR(n downto 0);
        Tout : out STD_LOGIC);
end spt;

architecture Behavioral of spt is
signal T : STD_LOGIC_VECTOR(n+1 downto 0);
begin
    
    T(0) <= Tin;
    sum : for i in 0 to n generate
            sum_i : entity WORK.SE 
                        port map(X => X(i),
                                 Y => Y(i),
                                 Tin => T(i),
                                 S => S(i),
                                 Tout => T(i+1));
         end generate;
    Tout <= T(n+1);

end Behavioral;
