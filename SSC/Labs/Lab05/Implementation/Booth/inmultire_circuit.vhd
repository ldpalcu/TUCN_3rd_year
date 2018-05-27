----------------------------------------------------------------------------------
-- Company: 
-- Engineer: 
-- 
-- Create Date: 03/28/2018 10:22:26 AM
-- Design Name: 
-- Module Name: inmultire_circuit - Behavioral
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

entity inmultire_circuit is
    generic (n : natural := 5);
    Port ( Clk : in STD_LOGIC;
           Rst : in STD_LOGIC;
           Start : in STD_LOGIC;
           X : in STD_LOGIC_VECTOR (n-1 downto 0);
           Y : in STD_LOGIC_VECTOR (n-1 downto 0);
           A : out STD_LOGIC_VECTOR (n-1 downto 0);
           Q : out STD_LOGIC_VECTOR (n-1 downto 0);
           Term : out STD_LOGIC);
end inmultire_circuit;

architecture Behavioral of inmultire_circuit is
    signal Bout, Aout, Qout, Sout, Bxor, TinXor  : STD_LOGIC_VECTOR (n-1 downto 0):=(others => '0');
    signal LoadA, LoadB, SubB, RstA, ShrAQ, LoadQ, RstQm1 : STD_LOGIC:='0';
    signal Qlast, BistOut, Tout, OVF: STD_LOGIC := '0';
    signal Q0Qm1 : STD_LOGIC_VECTOR (1 downto 0):="00";
    
begin
    Q0Qm1 <= Qout(0) & BistOut;
    
    UControl : entity WORK.UC
            generic map (n => n)
            port map (Start => Start,
                    Clk => Clk,
                    Rst => Rst,
                    Q0Qm1 => Q0Qm1,
                    Term => Term,
                    LoadA => LoadA,
                    LoadB => LoadB,
                    RstA => RstA,
                    SubB => SubB,
                    ShrAQ => ShrAQ,
                    LoadQ => LoadQ,
                    RstQm1 => RstQm1);
                    
            
    B : entity WORK.FDN
            generic map ( n => n )
            port map (Clk => Clk,
                      D => X,
                      Q => Bout,
                      Rst => Rst,
                      CE => LoadB);
                      
    Qminus: entity WORK.FD
            port map (Clk => Clk,
                      D => Qout(0),
                      Q => BistOut,
                      Rst => RstQm1,
                      CE => ShrAQ);
    
    AReg : entity WORK.SRRN
            generic map ( n => n)
            port map (Clk => Clk,
                      D => Sout,
                      Q => Aout,
                      SRI => Aout(n-1),
                      Rst => RstA,
                      Load => LoadA,
                      CE => ShrAQ);
                      
            
    QReg : entity WORK.SRRN
            generic map (n => n)
            port map (Clk => Clk,
                      D => Y,
                      Q => Qout,
                      SRI => Aout(0),
                      Rst => Rst,
                      Load => LoadQ,
                      CE => ShrAQ);
                      
    Adder: entity WORK.ADDN
            generic map (n => n)
            port map (X => Bxor,
                      Y => Aout,
                      Tin => SubB,
                      S => Sout,
                      Tout => Tout,
                      OVF => OVF);
    
    Bxor <= TinXor xor Bout;
    TinXor <= (others => SubB);
                      
    A <= Aout;
    Q <= Qout;

end Behavioral;
