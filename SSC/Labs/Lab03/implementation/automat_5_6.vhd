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

entity automat_5_6 is
    Port ( Clk : in STD_LOGIC;
           Rst : in STD_LOGIC;
           Data : in STD_LOGIC;
           Flag : out STD_LOGIC);
end automat_5_6;

architecture Behavioral of automat_5_6 is
    type TIP_STARE is (s0, s1, s2, s3, s4, s5, s6, s7);
    signal Stare : TIP_STARE;
    signal detect : STD_LOGIC := '0';
begin
    proc1: process(Clk)
    begin
        if RISING_EDGE(Clk) then
            if Rst = '1' then
                Stare <= s0;
            else
                case Stare is
                    when s0 =>
                        if Data = '1' then
                            Stare <= s1;
                        end if;
                        detect <= '0';
                    when s1 =>
                        if Data = '1' then
                            Stare <= s2;
                        else
                            Stare <= s0;
                        end if;
                    when s2 =>
                        if Data = '1' then
                            Stare <= s3;
                        else 
                            Stare <= s0;
                        end if;
                    when s3 =>
                        if Data = '1' then
                            Stare <= s1;
                        else
                            Stare <= s4;
                        end if;
                    when s4 =>
                        if Data = '1' then
                            Stare <= s5;
                        else
                            Stare <= s0;
                        end if;
                    when s5 =>
                        if Data = '1' then
                            Stare <= s1;
                        else
                            Stare <= s6;
                        end if;
                    when s6 =>
                        if Data = '1' then
                            Stare <= s7;
                        else
                            Stare <= s0;
                        end if;
                    when s7 =>
                        if Data = '1' then
                            detect <= '1';
                        else
                            detect <= '1';
                        end if;
                        Stare <= s0;
                 end case;
            end if;
         end if;
    end process proc1;
    
    flag <= '1' when detect = '1' else
            '0';
                                
end Behavioral;
