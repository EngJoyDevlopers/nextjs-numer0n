'use client';

import React from 'react';

// 進行状況などゲームの状態を表示するためのコンポーネント
interface GameBoardProps {
  gameState: string;
}

const GameBoard: React.FC<GameBoardProps> = ({ gameState }) => {
  return (
    <div className="game-board">
      <p>{gameState}</p>
    </div>
  );
};

export default GameBoard;
