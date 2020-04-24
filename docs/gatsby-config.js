module.exports = {
  siteMetadata: {
    title: 'Event Driven Architecture',
    description: 'This project represents the body of knowledge around event-driven architecture and can be considered as a live book, we are writing from our consulting engagements',
    keywords: 'gatsby,theme,carbon',
    repository: {
      baseUrl: 'https://github.com/ibm-cloud-architecture/refarch-eda',
      subDirectory: '/docs-gatsby',
      branch: 'master'
    }
  },
  pathPrefix: `/refarch-eda`,
  plugins: [
    {
      resolve: 'gatsby-plugin-manifest',
      options: {
        name: 'Carbon Design Gatsby Theme',
        short_name: 'Gatsby Theme Carbon',
        start_url: '/',
        background_color: '#ffffff',
        theme_color: '#0062ff',
        display: 'browser',
      },
    },
    {
      resolve: 'gatsby-theme-carbon',
      options: {
       isSearchEnabled: true,
       titleType: 'append'
      },
    },
    {
      resolve: `gatsby-plugin-google-analytics`,
      options: {
        trackingId: "UA-149377589-3"
      }
    }
  ],
};
