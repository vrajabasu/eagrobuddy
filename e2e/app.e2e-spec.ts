import { CCMLandingPage } from './app.po';

describe('CCM App', () => {
  let page: CCMLandingPage;

  beforeEach(() => {
    page = new CCMLandingPage();
  });

  it('should display message saying app works', () => {
    page.navigateTo();
    expect(page.getParagraphText()).toEqual('app works!');
  });
});
