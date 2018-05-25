
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
    variable VecTest   : STD_LOGIC_VECTOR(3 downto 0);
    variable RezCorect : STD_LOGIC_VECTOR(7 downto 0);
    variable NrErori   : INTEGER := 0;
    begin
        VecTest := x"0";
        
        for i in 0 to 15 loop
            bin_number <= VecTest;
            wait for 10 ns;
            
            if VecTest > x"9" then
                RezCorect(7 downto 4) := x"1";
                RezCorect(3 downto 0) := VecTest - x"9" - x"1";
            else
                RezCorect(7 downto 4) := x"0";
                RezCorect(3 downto 0) := VecTest;
            end if;
            
            if ((BCD1 & BCD0) /= RezCorect) then
                report " Rezultat asteptat BCD1" &
                    STD_LOGIC'image(RezCorect(7)) &
                    STD_LOGIC'image(RezCorect(5)) &
                    STD_LOGIC'image(RezCorect(6)) &
                    STD_LOGIC'image(RezCorect(4)) &
                    " Rezultat asteptat BCD0" &
                    STD_LOGIC'image(RezCorect(3)) &
                    STD_LOGIC'image(RezCorect(2)) &
                    STD_LOGIC'image(RezCorect(1)) &
                    STD_LOGIC'image(RezCorect(0)) &
                    " /=Valoarea obtinuta " &
                    STD_LOGIC'image(BCD1(3)) &
                    STD_LOGIC'image(BCD1(2)) &
                    STD_LOGIC'image(BCD1(1)) &
                    STD_LOGIC'image(BCD1(0)) &
                    STD_LOGIC'image(BCD0(3)) &
                    STD_LOGIC'image(BCD0(2)) &
                    STD_LOGIC'image(BCD0(1)) &
                    STD_LOGIC'image(BCD0(0)) &
                    " la momentul t = " &
                    TIME'image (now)
                    severity ERROR;
                NrErori := NrErori + 1;
             end if;
             VecTest := VecTest + 1;
         end loop;
         report "Testare terminata cu " &
          INTEGER'image (NrErori) & " erori";
          wait;    
    end process;
    


end Behavioral;
