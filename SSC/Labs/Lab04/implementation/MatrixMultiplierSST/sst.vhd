----------------------------------------------------------------------------------
-- Company: 
-- Engineer: 
-- 
-- Create Date: 05/26/2018 05:22:02 PM
-- Design Name: 
-- Module Name: sst - Behavioral
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

entity sst is
  Generic( n : natural := 8);
  Port (X : in STD_LOGIC_VECTOR(n downto 0);
        Y : in STD_LOGIC_VECTOR(n downto 0);
        Z : in STD_LOGIC_VECTOR(n downto 0);
        T : out STD_LOGIC_VECTOR(n downto 0);
        S : out STD_LOGIC_VECTOR(n downto 0));
end sst;

architecture Behavioral of sst is
signal Taux : STD_LOGIC;
begin

    sum : for i in 0 to n generate
            sum_i : entity WORK.SE 
                        port map(X => X(i),
                                 Y => Y(i),
                                 Tin => Z(i),
                                 S => S(i),
                                 Tout => T(i));
         end generate;


end Behavioral;
