
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

entity num4_tb is
--  Port ( );
end num4_tb;

architecture Behavioral of num4_tb is
    signal Clk : STD_LOGIC := '0';
    signal Rst : STD_LOGIC := '0';
    signal En  : STD_LOGIC := '0';
    signal Num : STD_LOGIC_VECTOR (3 downto 0)  :=  "0000";
    constant CLK_PERIOD : TIME := 10 ns;
begin
    --instantierea numaratorului
    DUT: entity WORK.num4 port map (
                Clk => Clk,
                Rst => Rst,
                En => En,
                Num => Num
                );
    
    --proces pt generarea ceasului 
    gen_clk: process
    begin
         Clk <= '0';
         wait for (CLK_PERIOD/2);
         Clk <= '1';
         wait for (CLK_PERIOD/2);
    end process gen_clk;
    
    --proces pt generarea semnalelor de intrare si verificarea iesirilor acestuia
    gen_vect_test: process
    variable RezCorect  : STD_LOGIC_VECTOR(3 downto 0) := "0000";
    variable NrErori    : INTEGER := 0;
    begin
        --generarea unui impuls de resetare
        Rst <= '1';
        wait for CLK_PERIOD;
        Rst <= '0';
        wait for CLK_PERIOD;
        En <= '1';
        --verificarea celor 16 iesiri posibile
        for i in 0 to 15 loop
            if (Num /= RezCorect) then 
                report "La momentul t = " &
                       TIME'image(now) &
                       " iesirea numaratorului este " &
                       STD_LOGIC'image (Num(3)) &
                       STD_LOGIC'image (Num(2)) &
                       STD_LOGIC'image (Num(1)) &
                       STD_LOGIC'image (Num(0)) &
                       " iesirea corecta este " &
                       STD_LOGIC'image (RezCorect(3)) &
                       STD_LOGIC'image (RezCorect(2)) &
                       STD_LOGIC'image (RezCorect(1)) &
                       STD_LOGIC'image (RezCorect(0))
                       severity ERROR;
                 NrErori := NrErori + 1;
             end if;
             wait for CLK_PERIOD;
             RezCorect := RezCorect + 1;
         end loop;
         if (NrErori = 0) then
                report "Simulare terminata cu succes";
         else       
                report "Testare terminata cu " &
                    INTEGER'image (NrErori) & " erori";
         end if;
         wait;
     end process gen_vect_test;

end Behavioral;
