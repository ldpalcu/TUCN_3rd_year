----------------------------------------------------------------------------------
-- Company: 
-- Engineer: 
-- 
-- Create Date: 05/09/2018 11:11:34 AM
-- Design Name: 
-- Module Name: DCDI - Behavioral
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

entity DCDI is
    Port ( Cod : in STD_LOGIC_VECTOR (31 downto 0);
           RegWr : out STD_LOGIC;
           AdrD : out STD_LOGIC_VECTOR(3 downto 0);
           MxD : out STD_LOGIC_VECTOR(1 downto 0);
           SSalt : out STD_LOGIC_VECTOR(1 downto 0);
           CSalt : out STD_LOGIC;
           MemWr : out STD_LOGIC;
           OpUAL : out STD_LOGIC_VECTOR(3 downto 0);
           MxA : out STD_LOGIC;
           MxB : out STD_LOGIC;
           AdrSA : out STD_LOGIC_VECTOR(3 downto 0);
           AdrSB : out STD_LOGIC_VECTOR(3 downto 0);
           SelC : out STD_LOGIC);
end DCDI;

architecture Behavioral of DCDI is

begin
    
    assign : process(Cod)
    begin
        case Cod is
            when "00000000" =>
                OpUAL <= "0000";
                RegWr <= '0';
                MxD   <= "00";
                SSalt <= "00";
                CSalt <= '0';
                MemWr <= '0';
                MxB   <= '0';
                MxA   <= '0';
                SelC  <= '0';
            when "01000000" =>
                OpUAL <= "0000";
                RegWr <= '1';
                MxD   <= "00";
                SSalt <= "00";
                CSalt <= '0';
                MemWr <= '0';
                MxB   <= '0';
                MxA   <= '0';
                SelC  <= '0';  
            when "00000010" =>
                OpUAL <= "0010";
                RegWr <= '1';
                MxD   <= "00";
                SSalt <= "00";
                CSalt <= '0';
                MemWr <= '0';
                MxB   <= '0';
                MxA   <= '0';
                SelC  <= '0';
            when "00000101" =>
                OpUAL <= "0101";
                RegWr <= '1';
                MxD   <= "00";
                SSalt <= "00";
                CSalt <= '0';
                MemWr <= '0';
                MxB   <= '0';
                MxA   <= '0';
                SelC  <= '0';
            when "00001000" =>
                OpUAL <= "1000";
                RegWr <= '1';
                MxD   <= "00";
                SSalt <= "00";
                CSalt <= '0';
                MemWr <= '0';
                MxB   <= '0';
                MxA   <= '0';
                SelC  <= '0';
            when "00001001" =>
                OpUAL <= "1001";
                RegWr <= '1';
                MxD   <= "00";
                SSalt <= "00";
                CSalt <= '0';
                MemWr <= '0';
                MxB   <= '0';
                MxA   <= '0';
                SelC  <= '0';
            when "00001010" => 
                OpUAL <= "1010";
                RegWr <= '1';
                MxD   <= "00";
                SSalt <= "00";
                CSalt <= '0';
                MemWr <= '0';
                MxB   <= '0';
                MxA   <= '0';
                SelC  <= '0';
            when "00001101" =>
                OpUAL <= "1101";
                RegWr <= '0';
                MxD   <= "00";
                SSalt <= "00";
                CSalt <= '0';
                MemWr <= '0';
                MxB   <= '0';
                MxA   <= '0';
                SelC  <= '0';
            when "00001110" =>
                OpUAL <= "1110";
                RegWr <= '1';
                MxD   <= "00";
                SSalt <= "00";
                CSalt <= '0';
                MemWr <= '0';
                MxB   <= '0';
                MxA   <= '0';
                SelC  <= '0';
            when "00100010" =>
                OpUAL <= "0010";
                RegWr <= '1';
                MxD   <= "00";
                SSalt <= "00";
                CSalt <= '0';
                MemWr <= '0';
                MxB   <= '1';
                MxA   <= '0';
                SelC  <= '1';
            when "00100101" =>
                OpUAL <= "0101";
                RegWr <= '1';
                MxD   <= "00";
                SSalt <= "00";
                CSalt <= '0';
                MemWr <= '0';
                MxB   <= '1';
                MxA   <= '0';
                SelC  <= '1';
            when "00101000" =>
                 OpUAL <= "1000";
                RegWr <= '1';
                MxD   <= "00";
                SSalt <= "00";
                CSalt <= '0';
                MemWr <= '0';
                MxB   <= '1';
                MxA   <= '0';
                SelC  <= '0';
            when "00101001" =>
                OpUAL <= "1001";
                RegWr <= '1';
                MxD   <= "00";
                SSalt <= "00";
                CSalt <= '0';
                MemWr <= '0';
                MxB   <= '1';
                MxA   <= '0';
                SelC  <= '0';
            when "00101010" =>
                 OpUAL <= "1010";
                RegWr <= '1';
                MxD   <= "00";
                SSalt <= "00";
                CSalt <= '0';
                MemWr <= '0';
                MxB   <= '1';
                MxA   <= '0';
                SelC  <= '0';
            when "01100000" =>
                 OpUAL <= "0000";
                RegWr <= '0';
                MxD   <= "00";
                SSalt <= "01";
                CSalt <= '0';
                MemWr <= '0';
                MxB   <= '1';
                MxA   <= '0';
                SelC  <= '1';
            when "01010000" =>
                OpUAL <= "0000";
                RegWr <= '0';
                MxD   <= "00";
                SSalt <= "01";
                CSalt <= '1';
                MemWr <= '0';
                MxB   <= '1';
                MxA   <= '0';
                SelC  <= '1';
            when "01101000" =>
                OpUAL <= "0000";
                RegWr <= '0';
                MxD   <= "00";
                SSalt <= "11";
                CSalt <= '0';
                MemWr <= '0';
                MxB   <= '1';
                MxA   <= '0';
                SelC  <= '1';
            when "01101001" =>
                OpUAL <= "0000";
                RegWr <= '0';
                MxD   <= "00";
                SSalt <= "11";
                CSalt <= '1';
                MemWr <= '0';
                MxB   <= '0';
                MxA   <= '0';
                SelC  <= '0';
        end case;
    end process;                                                                                                                                                                                                                                 
              

end Behavioral;
