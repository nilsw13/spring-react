import React from 'react'
import { Button } from '../ui/button'


const Home = () => {
  return (


   <>  

        <nav>
            <ul className='text-white bg-slate-900 flex flex-row items-center justify-center gap-4'>
                <li><a href="">Home</a></li>
                <li><a href="/login">Login</a></li>
                <li><a href="">Docs</a></li>
            </ul>
        </nav>

    <main>
        <div className='bg-slate-900 h-screen w-screen flex items-center justify-center '>
                <h1 className='text-white text-4xl font-bold'>Multi-tenant Saas Starter-kit</h1>
                <span className='text-white absolute top-[60%] font-medium '><span className='text-green-400 ml-8 mr-8'>SPRINGBOOT</span> + <span className='ml-8 text-blue-400'>REACT</span> & <span className='text-yellow-400'>VITE</span></span>
        </div>
    </main>
   </>


  )
}

export default Home