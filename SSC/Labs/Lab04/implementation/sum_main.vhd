----------------------------------------------------------------------------------
-- Company: 
-- Engineer: 
-- 
-- Create Date: 03/21/2018 11:09:55 AM
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
    Port ( Clk : in STD_LOGIC;
           Rst : in STD_LOGIC;
           X : in STD_LOGIC_VECTOR (7 downto 0);
           Y : in STD_LOGIC_VECTOR (7 downto 0);
           An : out STD_LOGIC_VECTOR (7 downto 0);
           Seg : out STD_LOGIC_VECTOR (7 downto 0));
end entity sum_main;

architecture Behavioral of sum_main is

    signal Data : STD_LOGIC_VECTOR(31 downto 0);
    signal S : STD_LOGIC_VECTOR(7 downto 0);
    signal Tout : STD_LOGIC;
    
begin
    sum: entity WORK.sum_8 port map(
            X => X,
            Y => Y,
            Tin => '0',
            S => S,
            Tout => Tout);
            
   Data(3 downto 0)   <= X(3 downto 0);
   Data(7 downto 4)   <= X(7 downto 4);
   Data(11 downto 8)  <= Y(3 downto 0);
   Data(15 downto 12) <= Y(7 downto 4);          
   Data(19 downto 16) <= "0000";
   Data(23 downto 20) <= "000" & Tout;
   Data(27 downto 24) <= S(3 downto 0);
   Data(31 downto 28) <= S(7 downto 4);
   
   displ7seg: entity WORK.displ7seg port map(
            Clk => Clk,
            Rst => Rst,
            Data => Data,
            An => An,
            Seg => Seg);
            
end Behavioral;
