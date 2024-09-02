'use client';
import SubmitButton from '@/components/elements/submitButton';
import React from 'react';
import { useSession, signOut } from 'next-auth/react';

export default function Page() {
  const { data: session } = useSession();
  return (
    <main className="flex min-h-screen flex-col p-6 grid place-items-center">
      <div className="grid grid-cols-2 grid-rows-1 gap-5">
      <p className='fixed left-0 top-0 flex w-full justify-center border-b border-gray-300 bg-gradient-to-b from-zinc-200 pb-6 pt-8 backdrop-blur-2xl dark:border-neutral-800 dark:bg-zinc-800/30 dark:from-inherit lg:static lg:w-auto  lg:rounded-xl lg:border lg:bg-gray-200 lg:p-4 lg:dark:bg-zinc-800/30'>
          Hello&nbsp;
          <code className='font-mono font-bold'>{session?.user?.name ?? 'guest'}</code>
        </p>
        
        {session && (
          <button
            onClick={() => signOut({ callbackUrl: 'http://localhost:3000/login' })}
            className='flex w-full justify-center border-2 border-b border-green-300 bg-green-200 pb-6 pt-8 backdrop-blur-2xl dark:border-green-800 dark:bg-green-800/30 lg:static lg:w-auto lg:rounded-xl lg:p-4 hover:border-green-400 hover:bg-green-300 dark:hover:border-green-900 dark:hover:bg-green-900/30'
          >
            Sign Out
          </button>
        )}
      </div>

      <h1 className="text-6xl w-full text-center">Numer0n</h1>
      <SubmitButton text="START" href="./setup"></SubmitButton>
    </main>
  );
}
