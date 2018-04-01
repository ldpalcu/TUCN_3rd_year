library IEEE;
use IEEE.STD_LOGIC_1164.ALL;

-- Uncomment the following library declaration if using
-- arithmetic functions with Signed or Unsigned values
--use IEEE.NUMERIC_STD.ALL;

-- Uncomment the following library declaration if instantiating
-- any Xilinx leaf cells in this code.
--library UNISIM;
--use UNISIM.VComponents.all;

entity automat_5_7 is
    Port ( Clk : in STD_LOGIC;
           Rst : in STD_LOGIC;
           Ready : in STD_LOGIC;
           Burst : in STD_LOGIC;
           RW : in STD_LOGIC;
           BusID : in STD_LOGIC_VECTOR (7 downto 0);
           OE : out STD_LOGIC;
           WE : out STD_LOGIC;
           Adr : out STD_LOGIC_VECTOR (1 downto 0));
end automat_5_7;

architecture Behavioral of automat_5_7 is
    type TIP_STARE is (idle, decision, write, read1, read2, read3, read4);
    signal Stare : TIP_STARE;
begin
    proc1: process(Clk)
    begin
        if rising_edge(Clk) then
            if Rst = '1' then
                Stare <= idle;
            else
                case Stare is
                    when idle =>
                        if BusID = x"A5" then
                            Stare <= decision;
                        end if;
                    when decision =>
                        if RW = '1' then
                            Stare <= read1;
                        else
                            Stare <= write;
                        end if;
                    when write =>
                        if Ready = '1' then
                            Stare <= idle;
                        end if;
                    when read1 => 
                        if Ready = '1' and Burst = '0' then
                            Stare <= idle;
                        elsif Ready = '1' and Burst = '1' then
                            Stare <= read2;
                        end if;
                    when read2 =>
                        if Ready = '1' then
                            Stare <= read3;
                        end if;
                    when read3 =>
                        if Ready = '1' then
                            Stare <= read4;
                        end if;
                    when read4 => 
                        if Ready = '1' then
                            Stare <= idle;
                        end if;
                 end case;
            end if;
        end if;
    end process proc1;
    
    proc2: process(Stare)
    begin
        case Stare is
            when idle     => OE <= '0'; WE <= '0'; Adr <= "00";
            when decision => OE <= '0'; WE <= '0'; Adr <= "00";
            when read1    => OE <= '1'; WE <= '0'; Adr <= "00";
            when read2    => OE <= '1'; WE <= '0'; Adr <= "01";
            when read3    => OE <= '1'; WE <= '0'; Adr <= "10";
            when read4    => OE <= '1'; WE <= '0'; Adr <= "11";
            when write    => OE <= '0'; WE <= '1'; Adr <= "00";                  
        end case;
    end process proc2;
end Behavioral;
