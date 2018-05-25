----------------------------------------------------------------------------------
-- Company: 
-- Engineer: 
-- 
-- Create Date: 03/28/2018 10:27:21 AM
-- Design Name: 
-- Module Name: FDN - Behavioral
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

entity FDN is
    generic (n : natural);
    Port ( Clk : in STD_LOGIC;
           D : in STD_LOGIC_VECTOR (n-1 downto 0);
           Rst : in STD_LOGIC;
           CE : in STD_LOGIC;
           Q : out STD_LOGIC_VECTOR (n-1 downto 0));
end FDN;

architecture Behavioral of FDN is

begin
    reg : process(Clk,Rst)
    begin
        if (rising_edge(Clk)) then
            if Rst = '1' then
                Q <= (others => '0');
            elsif CE = '1' then
                Q <= D;
            end if;
        end if;
    end process reg;


end Behavioral;
