
library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
use IEEE.STD_LOGIC_UNSIGNED.ALL;

-- Uncomment the following library declaration if using
-- arithmetic functions with Signed or Unsigned values
--use IEEE.NUMERIC_STD.ALL;

-- Uncomment the following library declaration if instantiating
-- any Xilinx leaf cells in this code.
--library UNISIM;
--use UNISIM.VComponents.all;

entity conv_BCD_tb is
--  Port ( );
end conv_BCD_tb;

architecture Behavioral of conv_BCD_tb is
    --declaratii semnale de intrare
    signal bin_number : STD_LOGIC_VECTOR (3 downto 0) := x"0";
    --declaratii semnale de iesire
    signal BCD1       : STD_LOGIC_VECTOR (3 downto 0);
    signal BCD0       : STD_LOGIC_VECTOR (3 downto 0);

begin
    --instantierea proiectului testat
    DUT: entity WORK.conv_BCD port map(
                bin_number => bin_number,
                BCD1 => BCD1,
                BCD0 => BCD0);
    --generare vectorilor de test si aplicarea acestora la intrari
    gen_vect_test: process
    variable VecTest    : STD_LOGIC_VECTOR(
    


end Behavioral;
