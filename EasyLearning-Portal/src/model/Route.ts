export interface ApplicationRoute {
  path: string;
  component: any;
}

export interface FullApplicationRoute extends ApplicationRoute {
  isProtected: boolean;
}
