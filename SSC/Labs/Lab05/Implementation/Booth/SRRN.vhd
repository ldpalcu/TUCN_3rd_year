----------------------------------------------------------------------------------
-- Company: 
-- Engineer: 
-- 
-- Create Date: 03/28/2018 10:34:06 AM
-- Design Name: 
-- Module Name: SRRN - Behavioral
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

entity SRRN is
    generic (n : natural);
    Port ( Clk : in STD_LOGIC;
           D : in STD_LOGIC_VECTOR (n-1 downto 0);
           Q : out STD_LOGIC_VECTOR (n-1 downto 0);
           SRI : in STD_LOGIC;
           Rst : in STD_LOGIC;
           Load : in STD_LOGIC;
           CE : in STD_LOGIC);
end SRRN;

architecture Behavioral of SRRN is
    signal Qint : STD_LOGIC_VECTOR( n-1 downto 0);
begin
    process(Clk)
    begin
        if rising_edge(Clk) then
            if Rst = '1' then
                Qint <= (others => '0');
            elsif Load = '1' then
                Qint <= D;
            elsif CE = '1' then
                Qint <= SRI & Qint(n-1 downto 1);
            end if;
        end if;
    end process;
    Q <= Qint;
   
end Behavioral;
