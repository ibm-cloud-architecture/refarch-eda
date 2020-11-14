import React from 'react';
import Footer from 'gatsby-theme-carbon/src/components/Footer';
import { Link } from 'gatsby';

const Content = ({ buildTime }) => (
    <span>
    <h4>Contribute:</h4>
    <p>As this solution is part of the Event-Driven Reference Architecture, the contribution policies apply the same way <Link to="/contribute/">here</Link>.</p>
    </span>
);

const links = {
  firstCol: [
    { linkText: 'Contributors:' },
    { href: 'https://www.linkedin.com/in/jeromeboyer/', linkText: 'Jerome Boyer' },
    { href: 'https://www.linkedin.com/in/rosowski/', linkText: 'Rick Osowski' },
    { href: 'https://www.linkedin.com/in/jesus-almaraz-hernandez/', linkText: 'Jesus Almaraz' },
    { href: 'https://www.linkedin.com/in/another-dave-jones/', linkText: 'David R Jones' },
    { href: 'https://www.linkedin.com/in/johannasaladas/', linkText: 'Johanna Saladas' },
    { href: 'https://www.linkedin.com/in/bikashmainali/', linkText: 'Bikash Mainali' },
    { href: 'https://www.linkedin.com/in/darneleadhemar/', linkText: 'Darnèle Adhemar' },
    { href: 'https://www.linkedin.com/in/ritu-patel-63379798//', linkText: 'Ritu Patel' },
    { href: 'https://www.linkedin.com/in/hemankita-perabathini/', linkText: 'Hemankita Perabathini' }
  ],
};

const CustomFooter = () => <Footer  Content={Content} links={links} />;

export default CustomFooter;
