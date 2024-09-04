import type { NextAuthOptions } from "next-auth"
import NextAuth from 'next-auth'
import Discord from "next-auth/providers/discord"

export const authOptions = {
    secret: process.env.NEXTAUTH_SECRET,
    providers: [
      Discord({
          clientId:     process.env.AUTH_DISCORD_ID     as string,
          clientSecret: process.env.AUTH_DISCORD_SECRET as string
      }),
    ],
  } satisfies NextAuthOptions

export const { handlers, auth, signIn, signOut } = NextAuth(authOptions)

