----------------------------------------------------------------------------------
-- Company: 
-- Engineer: 
-- 
-- Create Date: 05/09/2018 10:42:58 AM
-- Design Name: 
-- Module Name: SELMUXC - Behavioral
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

entity SELMUXC is
    Port ( SSalt : in STD_LOGIC_VECTOR (1 downto 0);
           CSalt : in STD_LOGIC;
           Z     : in STD_LOGIC;
           MxC   : out STD_LOGIC_vector(1 downto 0));
end SELMUXC;

architecture Behavioral of SELMUXC is

    signal conditie : STD_LOGIC_VECTOR(3 downto 0);

begin

    conditie <= SSalt & CSalt & Z;
    
    decizie : process(conditie)
    begin
        case conditie is
            when "0000" => 
                MxC <= "00";
            when "0001" => 
                Mxc <= "00";
            when "0010" =>
                MxC <= "00";
            when "0011" =>
                MxC <= "00";
            when "0100" =>
                MxC <= "00";
            when "0101" =>
                MxC <= "01";
            when "0110" =>
                MxC <= "01";
            when "0111" =>
                MxC <= "00";
            when "1000" =>
                MxC <= "10";
            when "1001" =>
                MxC <= "10";
            when "1010" =>
                MxC <= "10";
            when "1011" =>
                MxC <= "10";
            when "1100" =>
                MxC <= "01";
            when "1101" =>
                MxC <= "01";
            when "1110" =>
                MxC <= "11";
            when others =>
                MxC <= "11";             
        end case;
     end process;


end Behavioral;
