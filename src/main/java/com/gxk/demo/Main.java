package com.gxk.demo;

import com.gxk.demo.af.OutUtils;
import com.gxk.demo.af.output.FeedBack;
import com.gxk.demo.af.output.Icon;
import com.gxk.demo.af.output.Item;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Main {

  public static void main(String[] args) {
    if (args.length < 3) {
      FeedBack feedBack = OutUtils.notFound();
      System.out.print(feedBack);
      return;
    }
    // jump dir1 dir2 dir3 arg
    Main main = new Main();
    System.out.print(main.exec(args));
  }

  public FeedBack exec(String... args) {
    if (args.length < 3) {
      FeedBack feedBack = OutUtils.notFound();
      return feedBack;
    }

    // args parser
    int length = args.length;
    String arg = args[length - 1];

    List<String> dirArgs = new ArrayList<>();
    for (int i = 1; i < args.length-1; i++) {
      dirArgs.add(args[i]);
    }

    // 1
    List<Path> dirs = getValidPaths(dirArgs);

    // 2
    List<Item> items = getItems(dirs);

    if (items.isEmpty()) {
      return OutUtils.notFound();
    }

//    List<Item> rets = items.stream()
//      .filter(it -> it.getArg().contains(arg))
//      .collect(Collectors.toList());

    // 3
    List<Item> rets = filterItems(arg, items);

    if (rets.isEmpty()) {
      return OutUtils.notFound();
    }
    return new FeedBack(rets);
  }

  private List<Path> getValidPaths(List<String> args) {
    List<Path> dirs = new ArrayList<>();
    for (String arg : args) {
      Path path = Paths.get(arg);
      if (!path.toFile().exists()) {
        continue;
      }

      if (!path.toFile().isDirectory()) {
        continue;
      }

      dirs.add(path);
    }
    return dirs;
  }

  private List<Item> filterItems(String arg, List<Item> items) {
    return items.stream()
      .map(it -> new ScoreHolder(it, fuzzyScore(it.getUid(), arg)))
      .filter(it -> it.getScore() > 3)
      .sorted((pre, next) -> next.getScore().compareTo(pre.getScore()))
      .map(ScoreHolder::getItem)
      .limit(2)
      .collect(Collectors.toList());
  }

  private List<Item> getItems(List<Path> dirs) {
    List<Item> items = new ArrayList<>();
    for (Path dir : dirs) {
      for (File file : dir.toFile().listFiles()) {
        if (!file.isDirectory()) {
          continue;
        }

        boolean isIdea = file.listFiles(it -> it.isFile() && it.getName().equals("pom.xml")).length == 1;
        boolean isPy = file.listFiles(it -> it.isFile() && it.getName().equals("requirements.txt")).length == 1;

        Icon icon = new Icon("static/images/vscode.png");
        if (isIdea) {
          icon = new Icon("static/images/ij.png");
        }
        if (isPy) {
          icon = new Icon("static/images/python.png");
        }

        String argPrefix = "code ";
        if (isPy) {
          argPrefix = "charm ";
        }
        if (isIdea) {
          argPrefix = "idea ";
        }

        Item item = new Item(file.getName().toUpperCase(), file.getName(), file.getName(), argPrefix + file.getAbsolutePath(), file.getName(), icon);
        items.add(item);
      }
    }
    return items;
  }

  public Integer fuzzyScore(final CharSequence term, final CharSequence query) {
    if (term == null || query == null) {
      return -1;
    }

    // fuzzy logic is case insensitive. We normalize the Strings to lower
    // case right from the start. Turning characters to lower case
    // via Character.toLowerCase(char) is unfortunately insufficient
    // as it does not accept a locale.
    final String termLowerCase = term.toString().toLowerCase();
    final String queryLowerCase = query.toString().toLowerCase();

    // the resulting score
    int score = 0;

    // the position in the term which will be scanned next for potential
    // query character matches
    int termIndex = 0;

    // index of the previously matched character in the term
    int previousMatchingCharacterIndex = Integer.MIN_VALUE;

    for (int queryIndex = 0; queryIndex < queryLowerCase.length(); queryIndex++) {
      final char queryChar = queryLowerCase.charAt(queryIndex);

      boolean termCharacterMatchFound = false;
      for (; termIndex < termLowerCase.length()
        && !termCharacterMatchFound; termIndex++) {
        final char termChar = termLowerCase.charAt(termIndex);

        if (queryChar == termChar) {
          // simple character matches result in one point
          score++;

          // subsequent character matches further improve
          // the score.
          if (previousMatchingCharacterIndex + 1 == termIndex) {
            score += 2;
          }

          previousMatchingCharacterIndex = termIndex;

          // we can leave the nested loop. Every character in the
          // query can match at most one character in the term.
          termCharacterMatchFound = true;
        }
      }
    }

    return score;
  }

  static class ScoreHolder {
    private final Item item;
    private final Integer score;

    ScoreHolder(Item item, Integer score) {
      this.item = item;
      this.score = score;
    }

    public Item getItem() {
      return item;
    }

    public Integer getScore() {
      return score;
    }
  }
}
