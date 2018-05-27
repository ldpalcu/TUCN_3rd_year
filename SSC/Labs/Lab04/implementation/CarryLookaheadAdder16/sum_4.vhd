----------------------------------------------------------------------------------
-- Company: 
-- Engineer: 
-- 
-- Create Date: 05/26/2018 02:49:49 PM
-- Design Name: 
-- Module Name: sum_4 - Behavioral
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
--use IEEE.NUMERIC_STD.ALL;

-- Uncomment the following library declaration if instantiating
-- any Xilinx leaf cells in this code.
--library UNISIM;
--use UNISIM.VComponents.all;

entity sum_4 is
  Port (X : in STD_LOGIC_VECTOR(3 downto 0);
        Y : in STD_LOGIC_VECTOR(3 downto 0);
        Tin : in STD_LOGIC;
        S : out STD_LOGIC_VECTOR(3 downto 0);
        P : out STD_LOGIC;
        G : out STD_LOGIC );
end sum_4;

architecture Behavioral of sum_4 is
    signal p0,p1,p2,p3 : STD_LOGIC;
    signal g0,g1,g2,g3 : STD_LOGIC;
    signal T : STD_LOGIC_VECTOR(4 downto 0);

begin
    T(0) <= Tin;
    sum : for i in 0 to 3 generate
            S(i) <= X(i) xor Y(i) xor T(i);
            T(i+1) <= (X(i) and Y(i)) or ((X(i) or Y(i)) and T(i));
          end generate;
    
    g0 <= X(0) and Y(0);
    g1 <= X(1) and Y(1);
    g2 <= X(2) and Y(2);
    g3 <= X(3) and Y(3);
    
    p0 <= X(0) or Y(0);
    p1 <= X(1) or Y(1);
    p2 <= X(2) or Y(2);
    p3 <= X(3) or Y(3);
    
    P <= p3 and p2 and p1 and p0;
    G <= g3 or (p3 and g2) or (p3 and p2 and g1) or (p3 and p2 and p1 and g0);
    

end Behavioral;
