import './App.css';
import React from 'react';
import { combine_sample, generate_harmony, play_sample } from "./Services/magenta-service";

function App() {
    return (
        <div className='homepage'>
            <button onClick={() => combine_sample().then(new_sample => generate_harmony(new_sample))}> Generate Song </button>
        </div>
    );
}

export default App;
