
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

entity automat_5_5 is
    Port ( Rst : in STD_LOGIC;
           Clk : in STD_LOGIC;
           Sync : in STD_LOGIC;
           Data : in STD_LOGIC;
           PError : out STD_LOGIC);
end automat_5_5;

architecture Behavioral of automat_5_5 is
    type TIP_STARE is (idle, inc, odd, even);
    signal Stare : TIP_STARE;
    signal count : STD_LOGIC_VECTOR (2 downto 0) := "000";
    signal data_test : STD_LOGIC := '0';
begin
    proc1: process(Clk)
    begin
        if RISING_EDGE(Clk) then
            if Rst = '1' then
                Stare <= idle;
            else
                case Stare is
                    when idle =>
                        if Sync = '0' then
                            Stare <= inc;
                        end if;
                        count <= "000";
                    when inc =>
                        if count = "111" then
                            if data_test = '1' then
                                Stare <= even;
                            else
                                Stare <= odd;
                            end if;
                        else 
                            data_test <= data_test xor Data;
                            count <= count + 1;
                        end if;
                    when even =>
                        if Sync = '0' then
                            Stare <= idle;
                        end if;
                    when odd =>
                        if Sync = '0' then
                            Stare <= idle;
                        end if;
               end case;
            end if; 
        end if;
    end process proc1;
    
    proc2: process(Stare)
    begin
        case Stare is 
            when idle => PError <= '0';
            when inc  => PError <= '0';
            when odd  => PError <= '1';
            when even => PError <= '0';
        end case;
    end process proc2;
            
    
    
                       
                        
        


end Behavioral;
