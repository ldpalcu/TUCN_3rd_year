----------------------------------------------------------------------------------
-- Company: 
-- Engineer: 
-- 
-- Create Date: 05/09/2018 10:30:59 AM
-- Design Name: 
-- Module Name: CONST - Behavioral
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

entity CONST is
    Port ( Input : in STD_LOGIC_VECTOR(15 downto 0);
           SelC : in STD_LOGIC;
           ExtendedOutput : out STD_LOGIC_VECTOR(31 downto 0));
end CONST;

architecture Behavioral of CONST is
    signal OutputInit : STD_LOGIC_VECTOR(31 downto 0);

begin
    
       OutputInit(31 downto 16) <= (others => '0') when SelC = '0' else
                                   (others => '1') when Input(15) = '1' else
                                   (others => '0');                  
       OutputInit(15 downto 0)  <= Input;
                                                    

end Behavioral;
