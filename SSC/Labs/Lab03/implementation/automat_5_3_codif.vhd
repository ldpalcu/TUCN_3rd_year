
library IEEE;
use IEEE.STD_LOGIC_1164.ALL;

-- Uncomment the following library declaration if using
-- arithmetic functions with Signed or Unsigned values
--use IEEE.NUMERIC_STD.ALL;

-- Uncomment the following library declaration if instantiating
-- any Xilinx leaf cells in this code.
--library UNISIM;
--use UNISIM.VComponents.all;

entity automat_5_3_codif is
    Port ( Clk : in STD_LOGIC;
           Rst : in STD_LOGIC;
           Hit : in STD_LOGIC;
           Frame : in STD_LOGIC;
           OE : out STD_LOGIC;
           GO : out STD_LOGIC;
           ACT : out STD_LOGIC);
end automat_5_3_codif;

architecture Behavioral of automat_5_3_codif is
    signal Stare      : STD_LOGIC_VECTOR (3 downto 0);
    constant idle     : STD_LOGIC_VECTOR (3 downto 0) := "0000";
    constant decode   : STD_LOGIC_VECTOR (3 downto 0) := "0001";
    constant busy     : STD_LOGIC_VECTOR (3 downto 0) := "0010";
    constant xfer1    : STD_LOGIC_VECTOR (3 downto 0) := "1110";
    constant xfer2    : STD_LOGIC_VECTOR (3 downto 0) := "1010";  
begin
    proc1: process (Clk)
    begin
        if rising_edge(clk) then
            if (Rst = '1') then
                Stare <= idle;
            else
                case Stare is
                    when idle =>
                        if Frame = '1' then
                            Stare <= decode;
                        end if;
                    when decode =>
                        if Hit = '1' then
                            Stare <= xfer1;
                        else
                            Stare <= busy;
                        end if;
                    when busy =>
                        if Frame = '1' then
                            Stare <= idle;
                        end if;
                    when xfer1 =>
                        if Frame = '1' then
                            Stare <= xfer2;
                        end if;
                    when others =>
                        Stare <= idle;
               end case;
            end if;
          end if;
      end process proc1;
      
      OE <= Stare(3);
      GO <= Stare(2);
      ACT <= Stare(1);
     
end Behavioral;

