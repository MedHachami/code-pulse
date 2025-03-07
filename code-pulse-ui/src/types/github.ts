export interface GithubContent {
  name: string;
  path: string;
  sha: string;
  type: string;
  content?: string;
  download_url?: string;
}

export interface RepoContentDTO {
  contents: GithubContent[];
  currentPath: string;
} 