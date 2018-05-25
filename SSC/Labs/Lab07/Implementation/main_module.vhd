----------------------------------------------------------------------------------
-- Company: 
-- Engineer: 
-- 
-- Create Date: 04/11/2018 10:49:32 AM
-- Design Name: 
-- Module Name: main_module - Behavioral
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
use IEEE.STD_LOGIC_UNSIGNED.ALL;

-- Uncomment the following library declaration if using
-- arithmetic functions with Signed or Unsigned values
--use IEEE.NUMERIC_STD.ALL;

-- Uncomment the following library declaration if instantiating
-- any Xilinx leaf cells in this code.
--library UNISIM;
--use UNISIM.VComponents.all;

entity main_module is
    Port ( Clk : in STD_LOGIC;
           Rst : in STD_LOGIC;
           Start : in STD_LOGIC;
           Tx : out STD_LOGIC;
           TxRdy : out STD_LOGIC);
end main_module;

architecture Behavioral of main_module is
    signal TxData : STD_LOGIC_VECTOR (7 downto 0) := (others => '0');
    signal StartDeb : STD_LOGIC := '0';
    signal TxInt : STD_LOGIC := '1';
    signal TxRdyInt : STD_LOGIC := '0';
    
begin
    UART : entity WORK.uart_tx port map (
                TxData => TxData,
                Clk    => Clk,
                Rst    => Rst,
                Start  => StartDeb,
                Tx     => TxInt,
                TxRdy  => TxRdyInt);
    DEBOUNCE : entity WORK.debounce port map (
                Clk   => Clk,
                Rst   => Rst,
                D_in  => Start,
                Q_out => StartDeb);
    VIO : entity WORK.vio_0
                  PORT MAP (
                    clk => clk,
                    probe_in0 => TxInt & TxRdyInt,
                    probe_out0 => TxData
                  );
    Tx <= TxInt;
    TxRdy <= TxRdyInt;          
    
                


end Behavioral;
