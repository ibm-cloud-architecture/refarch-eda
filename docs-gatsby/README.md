# Gatsby Theme Carbon Starter

## What is this?

> Gatsby has implemented a new feature called [themes](https://www.gatsbyjs.org/docs/themes/). Themes encapsulate all of the configuration and implementation details of Gatsby websites. This is a starter-kit (boilerplate) that has a dependancy on the `gatsby-theme-carbon` package. It includes some sample content in the `src/pages` directory.

## What’s included?

- Carbon Elements and Carbon React
- [Emotion](https://emotion.sh) for React component styling
- [gatsby-mdx](https://gatsby-mdx.netlify.com/) with brand new markdown components

## How do I use it?

`gatsby-theme-carbon` at it’s core relies on [mdx](https://mdxjs.com/) for page creation. Check out the `src/pages` directory for some examples for using mdx.

A key feature of Gatsby themes is component shadowing. By simply placing a component into the `src/gatsby-theme-carbon/components` location, you can override components used by the theme. You can read more about component shadowing [here](https://www.gatsbyjs.org/docs/themes/api-reference#component-shadowing).

You’re also free to make your own components and use them in your MDX pages.

## Developing the docs

This is your primary method for starting up your Gatsby site for development.

1. `cd docs-gatsby`
2. `npm install`
3. `npm run dev`

## Building the docs to test locally

This is what you’ll use to bundle your site for production. Gatsby will minimize your images and create a static, blazing fast site in your public directory. It is preferred to use the `:prefix` option, as this will append all of your links with a `pathPrefix` specified in your `gatsby-config.js` file.

1. `cd docs-gatsby`
2. `npm run build:prefix`
3. `npm run serve:prefix`

## What’s Next?

- Migrating reusable MDX components
- Parameters to configure Carbon theme
