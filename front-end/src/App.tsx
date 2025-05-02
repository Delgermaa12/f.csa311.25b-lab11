import React from 'react';
import './App.css';
import { Cell } from './game';
import BoardCell from './Cell';

interface Props {}

interface GameState {
  cells: Cell[];
  currentPlayer: string;
  winner: string;
}

class App extends React.Component<Props, GameState> {
  private initialized: boolean = false;

  constructor(props: Props) {
    super(props);
    this.state = {
      cells: [],
      currentPlayer: '',
      winner: ''
    };
  }

  newGame = async () => {
    const response = await fetch('/newgame');
    const json = await response.json();
    this.setState({
      cells: json['cells'],
      currentPlayer: json['currentPlayer'],
      winner: json['winner']
    });
  }

  play(x: number, y: number): React.MouseEventHandler {
    return async (e) => {
      e.preventDefault();
      const response = await fetch(`/play?x=${x}&y=${y}`);
      const json = await response.json();
      this.setState({
        cells: json['cells'],
        currentPlayer: json['currentPlayer'],
        winner: json['winner']
      });
    };
  }

  createCell(cell: Cell, index: number): React.ReactNode {
    if (cell.playable)
      return (
        <div key={index}>
          <a href='/' onClick={this.play(cell.x, cell.y)}>
            <BoardCell cell={cell} />
          </a>
        </div>
      );
    else
      return (
        <div key={index}>
          <BoardCell cell={cell} />
        </div>
      );
  }

  componentDidMount(): void {
    if (!this.initialized) {
      this.newGame();
      this.initialized = true;
    }
  }

  render(): React.ReactNode {
    return (
      <div>
        <div id="instructions">
          {
            this.state.winner
              ? `Winner: ${this.state.winner}`
              : `Current player: ${this.state.currentPlayer}`
          }
        </div>
        <div id="board">
          {this.state.cells.map((cell, i) => this.createCell(cell, i))}
        </div>
        <div id="bottombar">
          <button onClick={this.newGame}>New Game</button>
          <button>Undo</button>
        </div>
      </div>
    );
  }
}

export default App;
