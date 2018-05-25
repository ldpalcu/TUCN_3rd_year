----------------------------------------------------------------------------------
-- Company: 
-- Engineer: 
-- 
-- Create Date: 05/02/2018 11:19:16 AM
-- Design Name: 
-- Module Name: main_main_module - Behavioral
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

entity main_main_module is
    Port ( Clk : in STD_LOGIC;
          Sel : in STD_LOGIC;
          Rst : in STD_LOGIC;
          Read : in STD_LOGIC;
          MISO : in STD_LOGIC;
          MOSI : out STD_LOGIC;
          SCLK : out STD_LOGIC;
          SS : out STD_LOGIC;
          An : out STD_LOGIC_VECTOR (7 downto 0);
          Seg : out STD_LOGIC_VECTOR (7 downto 0));
end main_main_module;

architecture Behavioral of main_main_module is
    signal Data1, Data2, Data : STD_LOGIC_VECTOR(31 downto 0);
    signal ReadB : STD_LOGIC;

begin

    Accelerometrul : entity WORK.main_module 
                        generic map(Dim_Cuv => 8)
                        port map(
                            Clk => Clk,
                            Rst => Rst,
                            Read => ReadB,
                            MISO => MISO,
                            MOSI => MOSI,
                            SCLK => SCLK,
                            SS => SS,
                            Data1 => Data1,
                            Data2 => Data2);
                            
     Data <= Data1 when Sel = '1' else Data2;
                            
     Displ7seg : entity WORK.displ7seg 
                        port map (Clk => Clk,
                                  Rst => Rst,
                                  Data => Data,
                                  An => An,
                                  Seg => Seg);
     
     Debounce : entity WORK.debounce
                        port map (Clk => Clk,
                                  Rst => Rst,
                                  D_IN => Read,
                                  Q_OUT => ReadB);
                                  
     
     

end Behavioral;
